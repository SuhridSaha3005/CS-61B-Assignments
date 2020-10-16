package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private final List<Point> pointList; // variable to store list of points

    public NaivePointSet(List<Point> points) {
        pointList = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point mainPoint = new Point(x, y);
        double inf = Double.POSITIVE_INFINITY; // any distance will be less than infinity
        Point minPoint = new Point(inf, inf);
        for (Point point : pointList) {
            if (Point.distance(mainPoint, point) < Point.distance(mainPoint, minPoint)) {
                minPoint = point; // nearest point changes every time we get new minimum
            }
        }
        return minPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
