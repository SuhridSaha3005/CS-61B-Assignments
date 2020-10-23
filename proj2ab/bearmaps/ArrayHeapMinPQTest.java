package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    private static final Random RAND = new Random(500);

    @Test
    public void testArrayHeapMin() {
        double item, priority;
        ExtrinsicMinPQ<Double> minPQ1, minPQ2;
        for (int i = 0; i < 100; i ++) {
            minPQ1 = new ArrayHeapMinPQ<>();
            minPQ2 = new NaiveMinPQ<>();
            for (int j = 0; j < 200; j++) {
                item = RAND.nextDouble();
                priority = RAND.nextDouble();
                minPQ1.add(item, priority);
                minPQ2.add(item, priority);
                assertEquals(minPQ1.size(), minPQ2.size());
            }
            for (int k = 0; k < 200; k++) {
                item = RAND.nextDouble();
                assertEquals(minPQ1.contains(item), minPQ2.contains(item));
            }
            for (int m = 0; m < 200; m++) {
                assertEquals(minPQ1.getSmallest(), minPQ2.getSmallest());
                assertEquals(minPQ1.removeSmallest(), minPQ2.removeSmallest());
                assertEquals(minPQ1.size(), minPQ2.size());
            }
        }
    }

    @Test
    public void testPriorityChange(){
        double priority;
        ExtrinsicMinPQ<Integer> minPQ1, minPQ2;
        for (int i = 0; i < 100; i ++) {
            minPQ1 = new ArrayHeapMinPQ<>();
            minPQ2 = new NaiveMinPQ<>();
            for (int j = 0; j < 1000; j ++) {
                priority = RAND.nextDouble();
                minPQ1.add(j, priority);
                minPQ2.add(j, priority);
            }
            for (int k = 0; k < 1000; k ++) {
                priority = RAND.nextDouble();
                minPQ1.changePriority(k, priority);
                minPQ2.changePriority(k, priority);
                assertEquals(minPQ1.getSmallest(), minPQ2.getSmallest());
            }
        }
    }

    /* @source CS61B Lab5: Used the printTimingTable method from Lab 5 */
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    @Test
    public void timingAdd() {
        int count = 31250;
        Stopwatch sw;
        ExtrinsicMinPQ<Integer> minPQ;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        while (count <= 2000000) {
            minPQ = new ArrayHeapMinPQ<>();
            sw = new Stopwatch();
            for (int i = 0; i < count; i ++) {
                minPQ.add(i, RAND.nextDouble());
            }
            Ns.add(count);
            times.add(sw.elapsedTime());
            count *= 2;
        }
        System.out.println("Timing table for ArrayHeapMinPQ.add()");
        printTimingTable(Ns, times, Ns);
    }

    @Test
    public void timingChangePriority() {
        int count = 31250;
        Stopwatch sw;
        ExtrinsicMinPQ<Integer> minPQ;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> Ops = new ArrayList<>();
        while (count <= 2000000) {
            minPQ = new ArrayHeapMinPQ<>();
            for (int i = 0; i < count; i ++) {
                minPQ.add(i, RAND.nextDouble());
            }
            sw = new Stopwatch();
            for (int j = 0; j < count; j ++) {
                minPQ.changePriority(j, RAND.nextDouble());
            }
            Ns.add(count);
            times.add(sw.elapsedTime());
            Ops.add(count);
            count *= 2;
        }
        System.out.println("Timing table for ArrayHeapMinPQ.changePriority()");
        printTimingTable(Ns, times, Ops);
    }

    @Test
    public void timingRemoveSmallest() {
        int count = 31250;
        Stopwatch sw;
        ExtrinsicMinPQ<Integer> minPQ;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> Ops = new ArrayList<>();
        while (count <= 1000000) {
            minPQ = new ArrayHeapMinPQ<>();
            for (int i = 0; i < count; i ++) {
                minPQ.add(i, RAND.nextDouble());
            }
            sw = new Stopwatch();
            for (int j = 0; j < count; j ++) {
                minPQ.removeSmallest();
            }
            Ns.add(count);
            times.add(sw.elapsedTime());
            Ops.add(count);
            count *= 2;
        }
        System.out.println("Timing table for ArrayHeapMinPQ.removeSmallest()");
        printTimingTable(Ns, times, Ops);
    }
}
