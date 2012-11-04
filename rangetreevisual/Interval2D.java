package rangetreevisual;

public class Interval2D {
    private Interval x;
    private Interval y;
    
    public Interval2D(Point left, Point right) {
        x = new Interval(left.getX(), right.getX());
        y = new Interval(left.getY(), right.getY());
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
