import java.util.LinkedList;
import java.util.List;

public class BootstrappedQueue<T> implements Queue<T> {

    private final int lenfm;
    private final List<T> f;
    private final Queue<List<T>> m;
    private final int lenr;
    private final List<T> r;

    private BootstrappedQueue(int lenfm, List<T> f, Queue<List<T>> m, int lenr, List<T> r) {
        this.lenfm = lenfm;
        this.f = f;
        this.m = m;
        this.lenr = lenr;
        this.r = r;
    }

    private static final BootstrappedQueue<Object> E = new BootstrappedQueue<>(0, null, null, 0, null);

    public static <T> BootstrappedQueue<T> empty() {
        return (BootstrappedQueue<T>) E;
    }

    private static <T> BootstrappedQueue<T> checkQ(int lenfm, List<T> f, Queue<List<T>> m, int lenr, List<T> r) {
        if (lenr <= lenfm) {
            return checkF(lenfm, f, m, lenr, r);
        } else {
            return checkF(lenfm + lenr, f, m.enQueue(Util.reverse(r)), 0, new LinkedList<>());
        }
    }

    private static <T> BootstrappedQueue<T> checkF(int lenfm, List<T> f, Queue<List<T>> m, int lenr, List<T> r) {
        if (f.isEmpty() && m.isEmpty()) {
            return empty();
        }
        if (f.isEmpty()) {
            return new BootstrappedQueue<>(lenfm, m.head(), m.deQueue(), lenr, r);
        }
        return new BootstrappedQueue<>(lenfm, f, m, lenr, r);
    }

    @Override
    public Queue<T> enQueue(T t) {
        if (this.isEmpty()) {
            return new BootstrappedQueue<>(1, List.of(t), empty(), 0, new LinkedList<>());
        } else {
            return checkQ(this.lenfm, this.f, this.m, this.lenr + 1, Util.cons(t, this.r));
        }
    }

    @Override
    public Queue<T> deQueue() {
        if (this.isEmpty()) {
            return null;
        }
        return checkQ(this.lenfm - 1, Util.tail(this.f), this.m, this.lenr, this.r);
    }

    @Override
    public T head() {
        if (this.isEmpty()) {
            return null;
        }
        return this.f.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.m == null;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();

        buf.append("lenfm: ");
        buf.append(this.lenfm);

        buf.append(", f: ");
        buf.append(f);

        buf.append(", m: ");
        buf.append(m);

        buf.append(", lenr: ");
        buf.append(this.lenr);

        buf.append(", r: ");
        buf.append(r);

        return buf.toString();
    }
}
