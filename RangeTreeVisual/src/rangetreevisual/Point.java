package rangetreevisual;

import java.util.Comparator;

public class Point {
     int x;
     int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;        
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean equals(Point other) {
        return (this.x == other.x && this.y == other.y);
    }
    
    public static class XPointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            return p1.x - p2.x;
        }
    }
    
    public XPointComparator getXComparator() {
        return new XPointComparator();
    }
    
    public int compareTo(Point p) {
        if (this.y < p.y) {
            return -1;
        } else {
            if (this.y > p.y) {
                return 1;
            } else {
                if (this.y == p.y) {
                    return this.x - p.x;
                }
            }
        }
        return 0;
    }
    
    public static class XYPointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1.y < p2.y) {
                return -1;
            } else {
                if (p1.y > p2.y) {
                    return 1;
                } else {
                    if (p1.y == p2.y) {
                        return p1.x - p2.x;
                    }
                }
            }
            return 0;
        }
    }
    
    public XYPointComparator getXYPointComparator() {
        return new XYPointComparator();
    }
}
