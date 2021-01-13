import java.awt.Font;
import java.util.Comparator;

import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    public Point(int x, int y) // constructs the point (x, y)
    {
        this.x = x ;
        this.y = y ;
    }

    public void draw() {
        StdDraw.textRight(x, y, toString());
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        draw();
        that.draw();
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if(this.y < that.y) return -1;
        if(this.y > that.y) return 1;
        if(this.x < that.x) return -1;
        if(this.x > that.x) return 1;
        return 0;
    }

    public double slopeTo(Point that) {
        if(that.x == this.x && that.y == this.y) return Double.NEGATIVE_INFINITY;
        if(that.y == this.y) return 0.0;
        if(that.x == this.x) return Double.POSITIVE_INFINITY;
        return ((double)(that.y - this.y)) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopOrderComparator();
    }

    private class SlopOrderComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double delta = slopeTo(o1) - slopeTo(o2);
            if(delta == 0.0) return 0;
            return delta < 0.0 ? -1 : 1;
        }

    }

    private int x;
    private int y;
}
