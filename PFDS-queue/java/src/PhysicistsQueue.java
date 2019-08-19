import java.util.LinkedList;
import java.util.List;

public class PhysicistsQueue<T> implements Queue<T> {

    private final List<T> w;
    private final int lenf;
    private final List<T> f;
    private final int lenr;
    private final List<T> r;

    private PhysicistsQueue(List<T> w, int lenf, List<T> f, int lenr, List<T> r) {
        this.w = w;
        this.lenf = lenf;
        this.f = f;
        this.lenr = lenr;
        this.r = r;
    }

    public static <T> PhysicistsQueue<T> empty() {
        return new PhysicistsQueue<>(
                new LinkedList<>(),
                0,
                new LinkedList<>(),
                0,
                new LinkedList<>());
    }

    private static <T> PhysicistsQueue<T> check(List<T> w, int lenf, List<T> f, int lenr, List<T> r) {
        if (lenr <= lenf) {
            return checkw(w, lenf, f, lenr, r);
        } else {
            var newf = new LinkedList();
            newf.addAll(f);
            newf.addAll(r.stream().collect(LinkedList::new,
                    (l, e) -> l.add(0, e),
                    (l, subl) -> l.addAll(0, subl)));
            return checkw(f, lenf + lenr, newf, 0, new LinkedList<>()) ;
        }
    }

    private static <T> PhysicistsQueue<T> checkw(List<T> w, int lenf, List<T> f, int lenr, List<T> r) {
        if (w.isEmpty()) {
            return new PhysicistsQueue<>(f, lenf, f, lenr, r);
        } else {
            return new PhysicistsQueue<>(w, lenf, f, lenr, r);
        }
    }

    @Override
    public Queue<T> enQueue(T t) {
        var r = new LinkedList<T>();
        r.add(t);
        if (this.lenr > 0) {
            r.addAll(this.r);
        }
        return check(this.w, this.lenf, this.f, this.lenr + 1, r);
    }

    @Override
    public Queue<T> deQueue() {
        if (this.lenf == 0) {
            return null;
        }
        return check(
                this.w,
                this.lenf - 1,
                this.f.subList(1, this.f.size()),
                this.lenr,
                this.r);
    }

    @Override
    public T head() {
        if (this.lenf == 0) {
            return null;
        }
        return this.f.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.lenf == 0 && this.lenr == 0;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();

        buf.append("w: ");
        for (var v : this.w) {
            buf.append(v.toString());
            buf.append(", ");
        }

        buf.append("lenf: ");
        buf.append(this.lenf);
        buf.append(", ");

        buf.append("f: ");
        for (var v : this.f) {
            buf.append(v.toString());
            buf.append(", ");
        }


        buf.append("lenr: ");
        buf.append(this.lenr);
        buf.append(", ");

        buf.append("r: ");

        //for (var v : this.r.stream().collect(ArrayList::new, (l, e) -> l.add(0, e), (l, subl) -> l.addAll(0, subl))) {
        for (var v : this.r) {
            buf.append(v.toString());
            buf.append(", ");
        }

        return buf.toString();
    }
}
