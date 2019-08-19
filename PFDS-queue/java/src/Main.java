import java.util.stream.IntStream;

public class Main {

    private static void runBench(int len, Queue<Integer> queue) {
        System.out.print("start ... ");
        var start = System.currentTimeMillis();

        System.out.println();
        for (var i : IntStream.range(0, len + 1).toArray()) {
            queue = queue.enQueue(i);

            /*
            System.out.print(i);
            System.out.print(": ");
            //System.out.println(queue.head());
            System.out.println(queue);

             */
        }

        for (var i : IntStream.range(0, len).toArray()) {
            queue = queue.deQueue();

            /*
            System.out.print(i);
            System.out.print(": ");
            //System.out.println(queue.head());
            System.out.println(queue);

             */
        }

        System.out.println(System.currentTimeMillis() - start);
        //System.out.println(queue.head());
    }

    public static void main(String[] args) {

        //Queue<Integer> queue = BatchedQueue.empty();
        //Queue<Integer> queue = BankersQueue.empty();
        //Queue<Integer> queue = PhysicistsQueue.empty();
        //Queue<Integer> queue = HoodMelvilleQueue.empty();
        Queue<Integer> queue = BootstrappedQueue.empty();
        //runBench(5, queue);
        runBench(5000, queue);
        runBench(10000, queue);
        runBench(20000, queue);

        System.out.println(queue);

    }
}
