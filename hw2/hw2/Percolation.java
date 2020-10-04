package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.List;
import java.util.ArrayList;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF bottomlessGrid;
    private final List<Integer> openSites;
    private final int size;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Grid size must be positive integer");
        }
        grid = new WeightedQuickUnionUF((N * N) + 2);
        bottomlessGrid = new WeightedQuickUnionUF((N * N) + 1);
        // 0 = top, N * N + 1 = bottom
        openSites = new ArrayList<>();
        size = N;
    }

    // Converts (row, col) to position in WeightedQuickUnion
    private int xyTo1D(int row, int col) {
        return (size * row) + col + 1;
    }

    private boolean inGrid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row/Column must be within range!");
        }
        int sitePos = xyTo1D(row, col);
        if (!isOpen(row, col)) {
            openSites.add(sitePos);
            int [] adj;
            if (row == 0) {
                grid.union(0, sitePos);
                bottomlessGrid.union(0, sitePos);
                adj = new int[]{sitePos + size};
            } else if (row == size - 1) {
                grid.union((size * size) + 1, sitePos);
                adj = new int[]{sitePos + size};
            } else if (col == 0) {
                adj = new int[]{sitePos - size, sitePos + 1, sitePos + size};
            } else if (col == size - 1) {
                adj = new int[]{sitePos - size, sitePos - 1, sitePos + size};
            } else {
                adj = new int[]{sitePos - size, sitePos - 1, sitePos + 1, sitePos + size};
            }
            for (int i : adj) {
                if (openSites.contains(i)) {
                    grid.union(sitePos, i);
                    bottomlessGrid.union(sitePos, i);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row/Column must be within range!");
        }
        return openSites.contains(xyTo1D(row, col));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("Row/Column must be within range!");
        }
        return bottomlessGrid.connected(0, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites.size();
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, (size * size) + 1);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) { }
}
