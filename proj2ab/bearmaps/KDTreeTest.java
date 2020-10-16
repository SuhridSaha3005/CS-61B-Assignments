package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    /* Random double generator; required for randomized testing */
    private static final Random RAND = new Random(500);
    /* Tests if KDTree.nearest works correctly */
    @Test
    public void testNearest() {
        List<Point> points;
        double x;
        double y;
        KDTree t1;
        NaivePointSet t2;
        for (int i = 0; i < 100; i += 1) {
            points = new ArrayList<>();
            for (int k = 0; k < 10; k += 1) {
                x = RAND.nextDouble();
                y = RAND.nextDouble();
                points.add(new Point(x, y));
            }
            t1 = new KDTree(points);
            t2 = new NaivePointSet(points);
            for (int j = 0; j < 10; j += 1) {
                x = RAND.nextDouble();
                y = RAND.nextDouble();
                assertEquals(t2.nearest(x, y), t1.nearest(x, y));
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

    /* Prints out timing test for k-d tree constructor */
    @Test
    public void timingKDTreeConstruction() {
        int count = 31250;
        Stopwatch sw;
        KDTree k;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        while (count <= 2000000) {
            k = new KDTree();
            sw = new Stopwatch();
            for (int i = 0; i < count; i += 1) {
                k.insert(new Point(RAND.nextDouble(), RAND.nextDouble()));
            }
            Ns.add(count);
            times.add(sw.elapsedTime());
            count *= 2;
        }
        System.out.println("Timing table for Kd-Tree Construction");
        printTimingTable(Ns, times, Ns);
    }

    /* Prints out timing tests for NaivePointSet.nearest */
    @Test
    public void timingNaiveNearest() {
        int N = 125;
        Stopwatch sw;
        NaivePointSet nps;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> Ops = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        Point p;
        while (N <= 1000) {
            for (int i = 0; i < N; i += 1) {
                points.add(new Point(RAND.nextDouble(), RAND.nextDouble()));
            }
            nps = new NaivePointSet(points);
            sw = new Stopwatch();
            for (int count = 0; count < 1000000; count += 1) {
                p = nps.nearest(RAND.nextDouble(), RAND.nextDouble());
            }
            Ns.add(N);
            times.add(sw.elapsedTime());
            Ops.add(1000000);
            N *= 2;
        }
        System.out.println("Timing table for Naive Nearest");
        printTimingTable(Ns, times, Ops);
    }

    /* Prints out timing tests for KDTree.nearest */
    /* Can call nearest a million times in less than 3 seconds! */
    @Test
    public void timingKDTreeNearest() {
        int N = 31250;
        Stopwatch sw;
        KDTree k;
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> Ops = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        Point p;
        while (N <= 1000000) {
            for (int i = 0; i < N; i += 1) {
                points.add(new Point(RAND.nextDouble(), RAND.nextDouble()));
            }
            k = new KDTree(points);
            sw = new Stopwatch();
            for (int count = 0; count < 1000000; count += 1) {
                p = k.nearest(RAND.nextDouble(), RAND.nextDouble());
            }
            Ns.add(N);
            times.add(sw.elapsedTime());
            Ops.add(1000000);
            N *= 2;
        }
        System.out.println("Timing table for Kd-Tree Nearest");
        printTimingTable(Ns, times, Ops);
    }
}
