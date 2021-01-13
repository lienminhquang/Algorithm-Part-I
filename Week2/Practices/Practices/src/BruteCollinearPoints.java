
import java.awt.Font;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] array;
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    private boolean[] taked;

    public BruteCollinearPoints(Point[] points) {
        array = points;
        segments = new LineSegment[array.length];
        taked = new boolean[array.length];
        BruteForce();
    }

    private void BruteForce() {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                for (int k = j + 1; k < array.length; k++) {
                    for (int h = k + 1; h < array.length; h++) {
                        if (taked[i] == true || taked[j] == true || taked[k] == true || taked[h] == true)
                            continue;

                        double slope1 = array[i].slopeTo(array[j]);
                        double slope2 = array[i].slopeTo(array[k]);
                        double slope3 = array[i].slopeTo(array[h]);
                        if (slope1 == slope2 && slope1 == slope3) {
                            numberOfSegments++;
                            int min = i;
                            int max = i;
                            if (array[j].compareTo(array[min]) < 0)
                                min = j;
                            if (array[k].compareTo(array[min]) < 0)
                                min = k;
                            if (array[h].compareTo(array[min]) < 0)
                                min = h;
                            if (array[j].compareTo(array[max]) > 0)
                                max = j;
                            if (array[k].compareTo(array[max]) > 0)
                                max = k;
                            if (array[h].compareTo(array[max]) > 0)
                                max = h;
                            taked[min] = true;
                            taked[max] = true;
                            segments[numberOfSegments - 1] = new LineSegment(array[min], array[max]);
                        }
                    }
                }
            }
        }
        LineSegment[] aux = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            aux[i] = segments[i];
        }
        segments = aux;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 8));

        In in = new In("input.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}