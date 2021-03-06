import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
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

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        SLList<Integer> lst = new SLList<>();
        int countNow = 1000;
        int countPrev = 0;
        Stopwatch sw;
        int last;
        while (countNow <= 128000) {
            for (int i = 0; i < countNow - countPrev; i += 1) {
                lst.addLast(i);
            }
            sw = new Stopwatch();
            for(int j = 0; j < 10000; j += 1) {
                last = lst.getLast();
            }
            Ns.add(countNow);
            times.add(sw.elapsedTime());
            opCounts.add(10000);
            countPrev = countNow;
            countNow = 2 * countNow;
        }
        printTimingTable(Ns, times, opCounts);
    }

}
