import java.util.*;

public class BatchedQueue<T> implements Queue<T> {


    private final List<T> f;
    private final List<T> r;

    private BatchedQueue(List<T> f, List<T> r) {
        this.f = f;
        this.r = r;
    }

    public static <T> Queue<T> empty() {
        return new BatchedQueue<>(new LinkedList<>(), new LinkedList<>());
    }

    private static <T> Queue<T> check(List<T> f, List<T> r) {
        if (f.isEmpty()) {
            return new BatchedQueue<>(
                    r.stream().collect(LinkedList::new, (l, e) -> l.add(0, e), (l, subl) -> l.addAll(0, subl)),
                    new LinkedList<>()) ;
        }
        return new BatchedQueue<>(f, r);
    }

    @Override
    public Queue<T> enQueue(T t) {
        var r = new LinkedList<T>();
        r.add(t);
        if (this.r.size() > 0) {
            r.addAll(this.r);
        }
        return check(this.f, r);
    }

    @Override
    public Queue<T> deQueue() {
        if (this.f.isEmpty()) {
            return null;
        }
        return check(this.f.subList(1, this.f.size()), this.r);
    }

    @Override
    public T head() {
        return this.f.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.f.isEmpty() && this.r.isEmpty();
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();

        buf.append("f: ");
        for (var v : this.f) {
            buf.append(v.toString());
            buf.append(", ");
        }

        buf.append("r: ");

        //for (var v : this.r.stream().collect(ArrayList::new, (l, e) -> l.add(0, e), (l, subl) -> l.addAll(0, subl))) {
        for (var v : this.r) {
            buf.append(v.toString());
            buf.append(", ");
        }

        return buf.toString();
    }
}

