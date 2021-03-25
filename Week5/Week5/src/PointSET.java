import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private final SET<Point2D> _pointSet; 


    public PointSET() {
        _pointSet = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return _pointSet.isEmpty();
    }

    public int size() {
        return _pointSet.size();
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        _pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        return _pointSet.contains(p);
    }

    public void draw() {
        for (Point2D point2d : _pointSet) {
            StdDraw.point(point2d.x(), point2d.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) {
            throw new IllegalArgumentException();
        }
        LinkedList<Point2D> list = new LinkedList<Point2D>();
        for (Point2D point2d : _pointSet) {
            if(rect.contains(point2d)) {
                list.add(point2d);
            }
        }

        return list;
    }

    public Point2D nearest(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        double minDis = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D point2d : _pointSet) {
            double dis =  p.distanceSquaredTo(point2d);
            if(dis < minDis)
            {
                nearest = point2d;
                minDis = dis;
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        PointSET kdtree = new PointSET();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }
    }
}
