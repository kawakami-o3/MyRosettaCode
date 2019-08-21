import java.util.function.Function;

/**
 * Purely Functional Data Structures P. 174
 */
public class ImplicitQueue<T> implements Queue<T> {

    private static class Lazy<T> {
        final T queue;
        final Function<T, T> fun;

        Lazy(T queue, Function<T, T> fun) {
            this.queue = queue;
            this.fun = fun;
        }

        static <T> Lazy<T> susp(T queue, Function<T, T> fun) {
            return new Lazy<T>(queue, fun);
        }

        T force() {
            return fun.apply(this.queue);
        }
    }

    private enum DigitType {
        Zero,
        One,
        Two,
    }

    private static class Digit<T> {
        final DigitType type;
        final T x;
        final T y;

        Digit(DigitType type, T x, T y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }

        private static final Digit<Object> ZERO = new Digit<>(DigitType.Zero, null, null);

        static <T> Digit<T> zero() {
            return (Digit<T>) ZERO;
        }

        static <T> Digit<T> one(T x) {
            return new Digit<>(DigitType.One, x, null);
        }

        static <T> Digit<T> two(T x, T y) {
            return new Digit<>(DigitType.Two, x, y);
        }

        @Override
        public String toString() {
            var buf = new StringBuilder();
            switch (this.type) {
                case Zero:
                    buf.append("Zero[");
                    break;
                case One:
                    buf.append("One[");
                    buf.append(this.x);
                    break;
                case Two:
                    buf.append("Two[");
                    buf.append(this.x);
                    buf.append(", ");
                    buf.append(this.y);
                    break;
            }
            buf.append("]");
            return buf.toString();
        }

    }

    private enum QueueType {
        Shallow,
        Deep,
    }

    private final QueueType type;
    private final Digit<T> f;
    private final Lazy<Queue<Pair<T>>> m;
    private final Digit<T> r;

    private ImplicitQueue(QueueType type, Digit<T> f, Lazy<Queue<Pair<T>>> m, Digit<T> r) {
        this.type = type;
        this.f = f;
        this.m = m;
        this.r = r;
    }

    private static <T> ImplicitQueue<T> shallow(Digit<T> f) {
        return new ImplicitQueue<>(QueueType.Shallow, f, null, null);
    }

    private static <T> ImplicitQueue<T> deep(Digit<T> f, Lazy<Queue<Pair<T>>> m, Digit<T> r) {
        return new ImplicitQueue<>(QueueType.Deep, f, m, r);
    }

    private static final ImplicitQueue<Object> E = shallow(Digit.zero());

    public static <T> ImplicitQueue<T> empty() {
        return (ImplicitQueue<T>) E;
    }

    @Override
    public Queue<T> enQueue(T t) {
        if (this.isEmpty()) {
            return shallow(Digit.one(t));
        }

        if (this.type == QueueType.Shallow && this.f.type == DigitType.One) {
            return deep(Digit.two(this.f.x, t), Lazy.susp(empty(), x -> x), Digit.zero());
        }

        if (this.r.type == DigitType.Zero) {
            return deep(this.f, this.m, Digit.one(t));
        }

        return deep(this.f, Lazy.susp(this.m.force(), x -> x.enQueue(Pair.create(this.r.x, t))), Digit.zero());
    }

    @Override
    public Queue<T> deQueue() {
        if (this.isEmpty()) {
            return null;
        }
        if (this.type == QueueType.Shallow && this.f.type == DigitType.One) {
            return empty();
        }
        if (this.type == QueueType.Deep && this.f.type == DigitType.Two) {
            return deep(Digit.one(this.f.y), this.m, this.r);
        }

        var m = this.m.force();
        if (m.isEmpty()) {
            return shallow(this.r);
        } else {
            var y = m.head().right;
            var z = m.head().left;
            return deep(Digit.two(y, z), Lazy.susp(m, x -> x.deQueue()), this.r);
        }
    }

    @Override
    public T head() {
        if (this.isEmpty()) {
            return null;
        }
        return this.f.x;
    }

    @Override
    public boolean isEmpty() {
        return this.type == QueueType.Shallow && this.f.type == DigitType.Zero;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();
        buf.append("type: ");
        buf.append(this.type);

        buf.append(", f: ");
        buf.append(this.f);

        buf.append(", m: ");
        buf.append(this.m);

        buf.append(", r: ");
        buf.append(this.r);

        return buf.toString();
    }
}
