import java.util.LinkedList;
import java.util.List;

public class Util {

    public static <T> List<T> cons(T t, List<T> lst) {
        var ret = new LinkedList<T>();
        ret.add(t);
        ret.addAll(lst);
        return ret;
    }

    public static <T> List<T> tail(List<T> lst) {
        if (lst.isEmpty()) {
            return new LinkedList<>();
        }
        var ret = new LinkedList<T>();
        ret.addAll(lst.subList(1, lst.size()));
        return ret;
    }
}
