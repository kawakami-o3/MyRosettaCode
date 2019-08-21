/**
 * Purely Functional Data Structures P. 174
 */
public class ImplicitStrictQueue<T> implements Queue<T> {

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

        public static <T> Digit<T> zero() {
            return (Digit<T>) ZERO;
        }

        public static <T> Digit<T> one(T x) {
            return new Digit<>(DigitType.One, x, null);
        }

        public static <T> Digit<T> two(T x, T y) {
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
    private final Queue<Pair<T>> m;
    private final Digit<T> r;

    private ImplicitStrictQueue(QueueType type, Digit<T> f, Queue<Pair<T>> m, Digit<T> r) {
        this.type = type;
        this.f = f;
        this.m = m;
        this.r = r;
    }

    private static <T> ImplicitStrictQueue<T> shallow(Digit<T> f) {
        return new ImplicitStrictQueue<>(QueueType.Shallow, f, null, null);
    }

    private static <T> ImplicitStrictQueue<T> deep(Digit<T> f, Queue<Pair<T>> m, Digit<T> r) {
        return new ImplicitStrictQueue<>(QueueType.Deep, f, m, r);
    }

    private static final ImplicitStrictQueue<Object> E = shallow(Digit.zero());

    public static <T> ImplicitStrictQueue<T> empty() {
        return (ImplicitStrictQueue<T>) E;
    }

    @Override
    public Queue<T> enQueue(T t) {
        if (this.isEmpty()) {
            return shallow(Digit.one(t));
        }

        if (this.type == QueueType.Shallow && this.f.type == DigitType.One) {
            return deep(Digit.two(this.f.x, t), empty(), Digit.zero());
        }

        if (this.r.type == DigitType.Zero) {
            return deep(this.f, this.m, Digit.one(t));
        }

        return deep(this.f, m.enQueue(Pair.create(this.r.x, t)), Digit.zero());
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

        if (this.m.isEmpty()) {
            return shallow(this.r);
        } else {
            var y = this.m.head().right;
            var z = this.m.head().left;
            return deep(Digit.two(y, z), this.m.deQueue(), this.r);
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
