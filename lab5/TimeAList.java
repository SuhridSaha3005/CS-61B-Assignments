import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        AList<Integer> lst = new AList<>();
        int countPrev = 0;
        int countNow = 1000;
        Stopwatch sw = new Stopwatch();
        while (countNow <= 128000) {
            for (int i = 0; i < countNow - countPrev; i += 1) {
                lst.addLast(i);
            }
            Ns.add(countNow);
            times.add(sw.elapsedTime());
            countPrev = countNow;
            countNow = 2 * countNow;
        }
        printTimingTable(Ns, times, Ns);
    }


}
