import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {


    private static void runBench(int len, Queue<Integer> queue) {
        //var len = 30000;
        System.out.print("start ... ");
        var start = System.currentTimeMillis();

        for (var i : IntStream.range(0, len + 1).toArray()) {
            queue = queue.enQueue(i);
        }

        for (var i : IntStream.range(0, len).toArray()) {
            //System.out.println(i);
            queue = queue.deQueue();
        }

        System.out.println(System.currentTimeMillis() - start);
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

        runBench(5000, PhysicistsQueue.empty());
        runBench(10000, PhysicistsQueue.empty());
        runBench(20000, PhysicistsQueue.empty());

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

        //var r = q.deQueue();
        //System.out.println(r);

        /*
        var l = List.of(1,2,3);

        var s = l.stream();
        Sytem.out.println(l);
         */
    }
}
