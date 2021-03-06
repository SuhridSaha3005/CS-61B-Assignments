package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] percolationData;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Trials & Grid size must be positive");
        }
        percolationData = new double[T];
        Percolation current;
        int row;
        int col;
        for (int i = 0; i < T; i += 1) {
            current = pf.make(N);
            while (!current.percolates()) {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                current.open(row, col);
            }
            double openSites = current.numberOfOpenSites();
            double totalSites = (double) N * N;
            percolationData[i] = openSites / totalSites;
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
