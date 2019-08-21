import java.util.stream.IntStream;

public class Main {

    private static void runBench(boolean verbose, int len, Queue<Integer> queue) {
        System.out.print("start ... ");
        var start = System.currentTimeMillis();

        System.out.println();
        for (var i : IntStream.range(0, len + 1).toArray()) {
            queue = queue.enQueue(i);

            if (verbose) {
                System.out.print(i);
                System.out.print(": ");
                //System.out.println(queue.head());
                System.out.println(queue);
            }
        }

        for (var i : IntStream.range(0, len).toArray()) {
            queue = queue.deQueue();

            if (verbose) {
                System.out.print(i);
                System.out.print(": ");
                //System.out.println(queue.head());
                System.out.println(queue);
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        //System.out.println(queue.head());
    }

    public static void main(String[] args) {
        //Queue<Integer> queue = BatchedQueue.empty();
        //Queue<Integer> queue = BankersQueue.empty();
        //Queue<Integer> queue = PhysicistsQueue.empty();
        //Queue<Integer> queue = HoodMelvilleQueue.empty();
        //Queue<Integer> queue = BootstrappedQueue.empty();
        //Queue<Integer> queue = ImplicitQueue.empty();
        Queue<Integer> queue = ImplicitQueue.empty();
        //runBench(true, 5, queue);
        runBench(false, 5000, queue);
        runBench(false, 10000, queue);
        runBench(false, 20000, queue);

        // NOTE Only for ImplicitQueue
        runBench(false, 10000000, queue);
        runBench(false, 20000000, queue);


        queue = ImplicitStrictQueue.empty();
        //runBench(true, 5, queue);
        runBench(false, 5000, queue);
        runBench(false, 10000, queue);
        runBench(false, 20000, queue);

        // NOTE Only for ImplicitQueue
        runBench(false, 10000000, queue);
        runBench(false, 20000000, queue);


        System.out.println(queue);
    }
}
