import java.awt.Font;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] array;
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    private Point[] taked;
    private int takedIncre = 0;

    public static void main(String[] args) {
        // read the n points from a file
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 8));

        In in = new In("input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        array = points.clone();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                throw new IllegalArgumentException();
        }
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                throw new IllegalArgumentException();
            if (i > 0 && array[i].compareTo(array[i - 1]) == 0)
                throw new IllegalArgumentException();
        }

        segments = new LineSegment[array.length];
        taked = new Point[array.length];
        Caculate();
    }

    private void Caculate() {
        for (int i = 0; i < array.length - 3; i++) {
            Point p = array[i];
            Arrays.sort(array, i + 1, array.length, p.slopeOrder());
            int numOfCollinear = 1;
            Point start = p;
            Point end = p;
            Point takedStart = p;
            Point takedEnd = p;
            LineSegment lineSeg = null;
            for (int j = i + 2; j < array.length; j++) {
                if (p.slopeTo(array[j]) == p.slopeTo(array[j - 1])) {
                    numOfCollinear++;
                    if (start == end && start == p) {
                        if (array[j - 1].compareTo(start) < 0) {
                            start = array[j - 1];
                            //takedStart = j - 1;
                        }
                        if (array[j - 1].compareTo(end) > 0) {
                            end = array[j - 1];
                            //takedEnd = j - 1;
                        }
                    }
                    if (array[j].compareTo(start) < 0) {
                        start = array[j];
                        //takedStart = j;
                    }
                    if (array[j].compareTo(end) > 0) {
                        end = array[j];
                        //takedEnd = j;
                    }

                    if (numOfCollinear >= 3) {
                        lineSeg = new LineSegment(start, end);

                        takedStart = start;
                        takedEnd = end;
                    }
                } else {
                    numOfCollinear = 1;
                    start = p;
                    end = p;
                }
            }
            if (lineSeg != null) {
                boolean a = false;
                for (Point point : taked) {
                    if(point == takedStart || point == takedEnd)
                    {
                        a = true;
                        break;
                    }
                }
                if (a == false) {
                    segments[numberOfSegments++] = lineSeg;
                    taked[takedIncre++] = takedStart;
                    taked[takedIncre++] = takedEnd;
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
        return segments.clone();
    }
}
