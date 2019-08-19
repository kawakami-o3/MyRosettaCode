public class Pair<T> {
    public static <T> Pair<T> create(T r, T l) {
        return new Pair(r, l);
    }

    public final T right;
    public final T left;

    private Pair(T r, T l) {
        this.right = r;
        this.left = l;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();
        buf.append("(");
        buf.append(this.right);
        buf.append(", ");
        buf.append(this.left);
        buf.append(")");
        return buf.toString();
    }
}
