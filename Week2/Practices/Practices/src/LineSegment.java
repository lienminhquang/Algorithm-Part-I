public class LineSegment {
    private Point start;
    private Point end;

    public LineSegment(Point p, Point q) {
        start = p;
        end = q;
    }
    public void draw() {
        start.drawTo(end);
    }
    public String toString() {
        return start.toString() + " -> " + end.toString();
    }
 }