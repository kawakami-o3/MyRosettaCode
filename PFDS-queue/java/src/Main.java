import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        /*
        runBench(5000, BatchedQueue.empty());
        runBench(10000, BatchedQueue.empty());
        runBench(20000, BatchedQueue.empty());
         */

        /*
        runBench(5000, BankersQueue.empty());
        runBench(10000, BankersQueue.empty());
        runBench(20000, BankersQueue.empty());
         */

        /*
        runBench(5000, PhysicistsQueue.empty());
        runBench(10000, PhysicistsQueue.empty());
        runBench(20000, PhysicistsQueue.empty());
        */

        //Queue<Integer> queue = BatchedQueue.empty();
        //Queue<Integer> queue = BankersQueue.empty();
        //Queue<Integer> queue = PhysicistsQueue.empty();
        Queue<Integer> queue = HoodMelvilleQueue.empty();
        //runBench(5, queue);

        runBench(5000, queue);
        runBench(10000, queue);
        runBench(20000, queue);

        System.out.println(queue);

        /*
        var len = 30000;

        System.out.println("start");
        var start = System.currentTimeMillis();

        for (var i : IntStream.range(0, len + 1).toArray()) {
            q = q.enQueue(i);
        }

        for (var i : IntStream.range(0, len).toArray()) {
            q = q.deQueue();
        }


        System.out.println(System.currentTimeMillis() - start);
        System.out.println(q);
        */
    }
}
