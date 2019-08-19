import java.util.LinkedList;
import java.util.List;

public class HoodMelvilleQueue<T> implements Queue<T> {

    private enum StateType {
        Idle,
        Reversing,
        Appending,
        Done,
    }

    private static class RotationState<T> {
        final StateType type;
        final int ok;
        final List<T> f;
        final List<T> newf;
        final List<T> r;
        final List<T> newr;

        RotationState(StateType type, int ok, List<T> f, List<T> newf, List<T> r, List<T> newr) {
            this.type = type;
            this.ok = ok;
            this.f = f;
            this.newf = newf;
            this.r = r;
            this.newr = newr;
        }

        public static <T> RotationState<T> idle() {
            return new RotationState<T>(StateType.Idle, 0, null, null, null, null);
        }

        public static <T> RotationState<T> reversing(int ok, List<T> f, List<T> newf, List<T> r, List<T> newr) {
            return new RotationState<T>(StateType.Reversing, ok, f, newf, r, newr);
        }

        public static <T> RotationState<T> appending(int ok, List<T> newf, List<T> newr) {
            return new RotationState<T>(StateType.Appending, ok, null, newf, null, newr);
        }

        public static <T> RotationState<T> done(List<T> newr) {
            return new RotationState<T>(StateType.Done, 0, null, newr, null, null);
        }

        @Override
        public String toString() {
            var buf = new StringBuilder();
            buf.append("state:");
            buf.append(this.type);
            buf.append(", ok:");
            buf.append(this.ok);
            buf.append(", f:");
            buf.append(this.f);
            buf.append(", newf:");
            buf.append(this.newf);
            buf.append(", r:");
            buf.append(this.r);
            buf.append(", newr:");
            buf.append(this.newr);

            return buf.toString();
        }
    }


    private final int lenf;
    private final List<T> f;
    private final RotationState<T> state;
    private final int lenr;
    private final List<T> r;

    private HoodMelvilleQueue(int lenf, List<T> f, RotationState<T> state, int lenr, List<T> r) {
        if (f == null) {
            throw new NullPointerException();
        }
        this.lenf = lenf;
        this.f = f;
        this.state = state;
        this.lenr = lenr;
        this.r = r;
    }

    public static <T> Queue<T> empty() {
        return new HoodMelvilleQueue<T>(
                0,
                new LinkedList<>(),
                RotationState.idle(),
                0,
                new LinkedList<>());
    }

    private static <T> RotationState<T> exec(RotationState<T> state) {
        if (state.type == StateType.Reversing) {
            if (!state.f.isEmpty() && !state.r.isEmpty()) {
                var ret = RotationState.reversing(
                        state.ok + 1,
                        Util.tail(state.f),
                        Util.cons(state.f.get(0), state.newf),
                        Util.tail(state.r),
                        Util.cons(state.r.get(0), state.newr));
                return ret;
            } else if (state.f.isEmpty() && state.r.size() == 1){
                return RotationState.appending(
                        state.ok,
                        state.newf,
                        Util.cons(state.r.get(0), state.newr));
            }
        }
        if (state.type == StateType.Appending) {
            if (state.ok == 0) {
                return RotationState.done(state.newr);
            } else if (!state.newf.isEmpty()) {
                return RotationState.appending(
                        state.ok - 1,
                        Util.tail(state.newf),
                        Util.cons(state.newf.get(0), state.newr));
            }
        }
        return state;
    }

    private static <T> RotationState<T> invalidate(RotationState<T> state) {
        if (state.type == StateType.Reversing) {
            return RotationState.reversing(state.ok-1, state.f, state.newf, state.r, state.newr);
        }

        if (state.type == StateType.Appending) {
            if (state.ok == 0) {
                return RotationState.done(Util.tail(state.newr));
            } else {
                return RotationState.appending(state.ok - 1, state.newf, state.newr);
            }
        }

        return state;
    }


    private static <T> Queue<T> exec2(int lenf, List<T> f, RotationState<T> state, int lenr, List<T> r) {
        var newstate = exec(exec(state));
        if (newstate.type == StateType.Done) {
            return new HoodMelvilleQueue<>(lenf, newstate.newf, RotationState.idle(), lenr, r);
        } else {
            return new HoodMelvilleQueue<>(lenf, f, newstate, lenr, r);
        }
    }

    private static <T> Queue<T> check(int lenf, List<T> f, RotationState<T> state, int lenr, List<T> r) {
        if (lenr <= lenf) {
            return exec2(lenf, f, state, lenr, r);
        } else {
            var newstate = RotationState.reversing(0, f, new LinkedList<>(), r, new LinkedList<>());
            return exec2(lenf + lenr, f, newstate, 0, new LinkedList<>());
        }
    }

    @Override
    public Queue<T> enQueue(T t) {
        return check(this.lenf, this.f, this.state, this.lenr+1, Util.cons(t, this.r));
    }

    @Override
    public Queue<T> deQueue() {
        if (this.lenf == 0) {
            return null;
        }
        return check(
                this.lenf - 1,
                Util.tail(this.f),
                invalidate(this.state),
                this.lenr,
                this.r);
    }

    @Override
    public T head() {
        if (this.f.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return this.f.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.lenf == 0;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder();

        buf.append("len: ");
        buf.append(this.lenf);
        buf.append(", ");
        buf.append(this.lenr);

        buf.append(", f: ");
        buf.append(this.f);

        buf.append(", r: ");
        buf.append(this.r);

        buf.append(", rs: ");
        buf.append(this.state);
        return buf.toString();
    }
}

