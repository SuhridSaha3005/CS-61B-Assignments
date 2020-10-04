package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] percolationData;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Trials and Grid size must be positive integers");
        }
        percolationData = new double[T];
        Percolation current;
        for (double threshold : percolationData) {
            current = pf.make(N);
            while (!current.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                current.open(row, col);
            }
            double openSites = current.numberOfOpenSites();
            double totalSites = N*N;
            threshold = openSites / totalSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationData);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationData);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * (stddev() / Math.sqrt(percolationData.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * (stddev() / Math.sqrt(percolationData.length)));
    }
}
