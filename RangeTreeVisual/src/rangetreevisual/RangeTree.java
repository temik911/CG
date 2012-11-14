package rangetreevisual;

import java.util.Vector;

public class RangeTree {
    private class Node{
        private Point value;                   
        private Node left;
        private Node right;           
        private RangeTree bst;

        Node(Point p) {
            value = p;
            left = null;
            right = null;
            bst = null;
        }
    }
    private Node root;
    private Vector<Point> answer = new Vector<Point>();;
    
    public void insert(Point p) {
        root = insert(root, p);
    }
    
    private Node insert(Node cur, Point p) {
        if (cur == null) {
            Node r = new Node(p);
            r.bst = new RangeTree();
            r.bst.put(p);
            return r;
        }
        cur.bst.put(p);
        if (p.getX() <= cur.value.getX()) {
            cur.left  = insert(cur.left,  p);
        } else {
            cur.right = insert(cur.right, p);
        }
        return cur;
    }
    
    private void put(Point p) {
        root = put(root, p);
    }
    
    private Node put(Node cur, Point p) {
        if (cur == null) {
            return new Node(p);
        }
        if (p.getY() <= cur.value.getY()) {
            cur.left  = put(cur.left,  p);
        } else {
            cur.right = put(cur.right, p);
        }
        return cur;
    }
    
    public void query2D(Interval2D rect) {
        Interval intervalX = rect.getIntervalX();
        Node h = root;
        while (h != null && !intervalX.contains(h.value.getX())) {
            if (intervalX.getHigh() <= h.value.getX()) {
                h = h.left;
            } else {
                if (h.value.getX() <= intervalX.getLow()) {
                    h = h.right;
                }
            }
        }
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }

        queryL(h.left,  rect);
        queryR(h.right, rect);
    }

    private void queryL(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }
        if (h.value.getX() >= rect.getIntervalX().getLow()) {
                if (h.right != null) {
                    enumerate(h.right.bst.root, rect);
                }
                queryL(h.left, rect);
        } else {
            queryL(h.right, rect);
        }
    }

    private void queryR(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }
        if (rect.getIntervalX().getHigh() > h.value.getX()) {
            if (h.left != null) {
                enumerate(h.left.bst.root, rect);
            }
            queryR(h.right, rect);
        } else {
            queryR(h.left, rect);
        }
    }
    
    private void enumerate(Node h, Interval2D rect) {
        Interval intervalY = rect.getIntervalY();
        while (h != null && !intervalY.contains(h.value.getY())) {
            if (intervalY.getHigh() <= h.value.getY()) {
                h = h.left;
            } else {
                if (h.value.getY() <= intervalY.getLow()) {
                    h = h.right;
                }
            }
        }
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }

        enumerateL(h.left,  rect);
        enumerateR(h.right, rect);
    }
    
    private void enumerateL(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }
        if (h.value.getY() >= rect.getIntervalY().getLow()) {
                if (h.right != null) {
                    addToAns(h.right);
                }
                enumerateL(h.left, rect);
        } else {
            enumerateL(h.right, rect);
        }
    }

    private void enumerateR(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.value)) {
            answer.add(h.value);
        }
        if (rect.getIntervalY().getHigh() > h.value.getY()) {
            if (h.left != null) {
                addToAns(h.left);
            }
            enumerateR(h.right, rect);
        } else {
            enumerateR(h.left, rect);
        }
    }
    
    private void addToAns(Node h) {
        if (h == null) {
            return;
        }
        if (h.left != null) {
            addToAns(h.left);
        }
        if (h.right != null) {
            addToAns(h.right);
        }
        answer.add(h.value);
    }
    
    
    public Vector<Point> getAnswer() {
        return answer;
    }
    
    public void clear() {
        answer.clear();
        root = null;
    }
    
    public void clearAnswer() {
        answer.clear();
    }
    
    public Vector<Point> getPoints(Node h, Vector<Point> points) {
        if (h != null) {
            points.add(h.value);
            getPoints(h.left, points);
            getPoints(h.right, points);
        }
        return points;
    }
    
    public Node getRoot(){
        return root;
    }
    
}
