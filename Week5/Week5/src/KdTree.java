import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private class Node {
        public Node(Point2D key) {
            _key = key;
        }
        Point2D _key;
        Node _left;
        Node _right;
    }

    private Node _root;
    private int _size;
    private Point2D _currentNearestPoint;
    private double _currentNearestDis;

    public KdTree() {
        _root = null;
        _size = 0;
    }

    public boolean isEmpty() {
        return _root == null;
    }

    public int size() {
        return _size;
    }

    public void insert(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        _root = _insert(p, _root, true);
    }

    private Node _insert(Point2D p, Node node, boolean horizontal)
    {
        if(node == null) {
            ++_size;
            return new Node(p);
        }

        if(p.equals(node._key)) {
            return node;
        }

        if(horizontal) {
            if(p.x() < node._key.x()) {
                node._left = _insert(p, node._left, !horizontal);
            }
            else {
                node._right = _insert(p, node._right, !horizontal);
            }
        }
        else {
            if(p.y() < node._key.y()) {
                node._left = _insert(p, node._left, !horizontal);
            }
            else {
                node._right = _insert(p, node._right, !horizontal);
            }
        }

        return node;
    }

    public boolean contains(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }
        Node current = _root;
        boolean horizontal = false;
        while(current != null)
        {
            if(p.equals(current._key))
            {
                return true;
            }
            if(!horizontal) {
                if(p.x() < current._key.x()) {
                    current = current._left;
                }
                else {
                    current = current._right;
                }
            }
            else {
                if(p.y() < current._key.y()) {
                    current = current._left;
                }
                else {
                    current = current._right;
                }
            }
            horizontal = !horizontal;
        }
        return false;
    }

    public void draw() {
       _draw(_root, false, 0, 0, 1, 1);
    }

    private void _draw(Node node, boolean horizontal, double minX, double minY, double maxX, double maxY) {
        if(node == null) {
            return;
        }
        if(horizontal) {
            _draw(node._left, !horizontal, minX, minY, maxX, node._key.y());
        }
        else {
            _draw(node._left, !horizontal, minX, minY, node._key.x(), maxY);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(node._key.x(), node._key.y(), 0.006);

        if(!horizontal) { // doc
            StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node._key.x(), minY, node._key.x(), maxY);
        }
        else { // ngang
            StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(minX, node._key.y(), maxX, node._key.y());
        }
        
        if(horizontal) {
            _draw(node._right, !horizontal, minX, node._key.y(), maxX, maxY);
        }
        else {
            _draw(node._right, !horizontal, node._key.x(), minY, maxX, maxY);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) {
            throw new IllegalArgumentException();
        }

        LinkedList<Point2D> result = new LinkedList<>();
        _range(rect, _root, false, new RectHV(0, 0, 1, 1), result);
        
        return result;
    }

    private void _range(RectHV rect, Node point, boolean horizontal, RectHV rectOfPoint, LinkedList<Point2D> result) {

        if(point == null){
            return;
        }

        if(!rect.intersects(rectOfPoint)) {
            return;
        }
        if(rect.contains(point._key)) {
            result.push(point._key);
        }
        if(point._left != null) {
            if(horizontal) {
                _range(rect, point._left, !horizontal, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), rectOfPoint.xmax(), point._key.y()), result);
            }
            else {
                _range(rect, point._left, !horizontal, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), point._key.x(), rectOfPoint.ymax()), result);
            }
        }
        if(point._right != null) {
            if(horizontal) {
                _range(rect, point._right, !horizontal, new RectHV(rectOfPoint.xmin(), point._key.y(), rectOfPoint.xmax(), rectOfPoint.ymax()), result);
            }
            else {
                _range(rect, point._right, !horizontal, new RectHV(point._key.x(), rectOfPoint.ymin(), rectOfPoint.xmax(), rectOfPoint.ymax()), result);
            }
        }
        
    }

    public Point2D nearest(Point2D p) {
        if(p == null) {
            throw new IllegalArgumentException();
        }

        _currentNearestDis =  Double.MAX_VALUE;
        _currentNearestPoint = null;

        _nearest(p, _root, new RectHV(0, 0, 1, 1), false);

        return _currentNearestPoint;
    }

    private void _nearest(Point2D p, Node point, RectHV rectOfPoint, boolean horizontal) {
        if(point == null){
            return;
        }

        if(rectOfPoint.distanceSquaredTo(p) >= _currentNearestDis) {
            return;
        }
        if(point._key.distanceSquaredTo(p) < _currentNearestDis) {
            //StdOut.print(_currentNearestDis + " -> ");
            _currentNearestDis = point._key.distanceSquaredTo(p);
            _currentNearestPoint = point._key;
           // StdOut.println(_currentNearestDis);
        }
        if(horizontal) {
            if(p.y() < point._key.y()) { // below -> left
                _nearest(p, point._left, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), rectOfPoint.xmax(), point._key.y()), !horizontal);
                _nearest(p, point._right, new RectHV(rectOfPoint.xmin(), point._key.y(), rectOfPoint.xmax(), rectOfPoint.ymax()), !horizontal);
                return;
            }
            else {
                _nearest(p, point._right, new RectHV(rectOfPoint.xmin(), point._key.y(), rectOfPoint.xmax(), rectOfPoint.ymax()), !horizontal);
                _nearest(p, point._left, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), rectOfPoint.xmax(), point._key.y()), !horizontal);
                return;
            }
        }
        else {
            if(p.x() < point._key.x()) { // -> left
                _nearest(p, point._left, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), point._key.x(), rectOfPoint.ymax()), !horizontal);
                _nearest(p, point._right, new RectHV(point._key.x(), rectOfPoint.ymin(), rectOfPoint.xmax(), rectOfPoint.ymax()), !horizontal);
                return;
            }
            else {
                _nearest(p, point._right, new RectHV(point._key.x(), rectOfPoint.ymin(), rectOfPoint.xmax(), rectOfPoint.ymax()), !horizontal);
                _nearest(p, point._left, new RectHV(rectOfPoint.xmin(), rectOfPoint.ymin(), point._key.x(), rectOfPoint.ymax()), !horizontal);
                return;
            }
        }
    }

    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
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
