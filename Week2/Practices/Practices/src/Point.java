import java.awt.Font;
import java.util.Comparator;

import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {


    public static void main(String[] args) {
        Point p = new Point(2,4);
        Point q = new Point(2,1);
        Point r = new Point(2,6);
        int a = p.slopeOrder().compare(q, r);
        StdOut.print(a+"");
    }

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
            double s1 = slopeTo(o1);
            double s2 = slopeTo(o2);
            if(s1 == s2) 
                return 0;
            Double delta = slopeTo(o1) - slopeTo(o2);
   
            return delta < 0.0 ? -1 : 1;
        }

    }

    //public double slope = 0;
    private int x;
    private int y;
}
