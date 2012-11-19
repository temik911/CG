package rangetreevisual;

public class Interval2D {
    private Interval x;
    private Interval y;
    
    public Interval2D(Point left, Point right) {
        if (left.getX() < right.getX()) {
            x = new Interval(left.getX(), right.getX());
        } else {
            x = new Interval(right.getX(), left.getX());
        }
        if (left.getY() < right.getY()) {
            y = new Interval(left.getY(), right.getY());
        } else {
            y = new Interval(right.getY(), left.getY());
        }
    }
    
    public Interval getIntervalX() {
        return x;
    }
    
    public Interval getIntervalY() {
        return y;
    }
    
    public boolean contains(Point p) {
        return (x.contains(p.getX()) && y.contains(p.getY()));
    }
}
