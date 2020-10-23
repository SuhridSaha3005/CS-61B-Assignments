package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Point point; // root point for k-d tree
    private KDTree left; // left child of k-d tree
    private KDTree right; // right child of k-d tree
    private KDTree parent; // to track parent of k-d tree node
    /* For any k-d tree k, k.left.parent = k.right.parent = k */
    private int depth; // to track depth of k-d tree node
    /* Even depths compare horizontally, odd depths compare vertically */

    /* Compares either x or y for node; dependent on depth */
    private double compare(Point p) {
        if (depth % 2 == 0) {
            return p.getX() - point.getX();
        } else {
            return p.getY() - point.getY();
        }
    }

    /* Inserts a point in k-d tree; public (not private) for testing purposes */
    public void insert(Point p) {
        if (point == null) {
            point = p; // since node is empty, make p the new node
            /* Every time we insert node, create empty left/right children */
            left = new KDTree();
            left.parent = this;
            right = new KDTree();
            right.parent = this;
            /* This allows us to identify root node & track any node depth */
            if (parent == null) {
                depth = 0;
            } else {
                depth = parent.depth + 1;
            }
        } else {
            /* Point p is inserted left/right depending on compare(p) */
            double cmp = compare(p);
            if (cmp < 0) {
                left.insert(p);
            } else {
                right.insert(p);
            }
        }
    }

    /* Constructor for empty k-d tree; public for testing purposes */
    public KDTree() {
        point = null;
        left = null;
        right = null;
        parent = null;
        depth = 0;
    }

    /* Main constructor for empty k-d tree; inserts each point from input list */
    public KDTree(List<Point> points) {
        for (Point p : points) {
            insert(p);
        }
    }

    /* Finds nearest point in k-d tree to the point (x, y) */
    @Override
    public Point nearest(double x, double y) {
        double inf = Double.POSITIVE_INFINITY;
        return nearest(this, new Point(x, y), new Point(inf, inf));
    }

    /* Helper method for nearest */
    private Point nearest(KDTree space, Point goal, Point best) {
        if (space == null || space.point == null) {
            return best; // returns best since node is empty
        }
        if (Point.distance(space.point, goal) < Point.distance(best, goal)) {
            best = space.point; // change best to node since node is closer
        }
        KDTree goodSide;
        KDTree badSide;
        double cmp = space.compare(goal);
        if (cmp < 0) {
            goodSide = space.left;
            badSide = space.right;
        } else {
            goodSide = space.right;
            badSide = space.left;
        }
        best = nearest(goodSide, goal, best);
        if (Math.pow(cmp, 2) <= Point.distance(best, goal)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }
}
