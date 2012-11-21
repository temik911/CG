package rangetreevisual;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class RangeTree {
    private class Node{
        int begin;
        int end;
        Node left;
        Node right;
        AVLTree avl;

        Node(int begin, int end) {
            this.begin = begin;
            this.end = end;
            left = null;
            right = null;
            avl = new AVLTree();
        }
    }
    public Node root;
    private Vector<Point> answer = new Vector<Point>();
    
    public void insert(List<Point> points) {
        Collections.sort(points, new Point().getXComparator());
        root = createTree(root, points, 0, points.size()-1);
        for (int i = 0; i < points.size(); i++) {
            insertPoint(root, points.get(i));
        }
    }
    
    public void insertPoint(Node cur, Point point) {
        cur.avl.root = cur.avl.insert(cur.avl.root, point);
        if ((cur.left != null) && (point.getX() < cur.left.end)) {
            insertPoint(cur.left, point);
        } else {
            if (cur.right != null) {
                insertPoint(cur.right, point);
            }
        }
    }
    
    public Node createTree(Node cur, List<Point> points, int k, int l) {
        if (l == k) {
            return null;
        }
        cur = new Node(points.get(k).getX(), points.get(l).getX());
        int m = (k+l)/2;
        if (l == m || k == m) {
            return cur;
        }
        cur.left = createTree(cur.left, points, k, m);
        cur.right = createTree(cur.right, points, m, l);
        return cur;  
    }
    
    public void query2D(Interval2D rect) {
        Interval intervalX = rect.getIntervalX();
        Node h = root;
        while (h != null && h.left != null && h.right != null) {
            if (intervalX.getLow() >= h.left.end) {
                h = h.right;
            } else {
                if (intervalX.getHigh() < h.right.begin) {
                    h = h.left;
                } else {
                    break;
                }
            }
        }
        queryL(h.left,  rect);
        queryR(h.right, rect);
    }

    private void queryL(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (h.left != null && h.left.end > rect.getIntervalX().getLow()) {
                if (h.right != null) {
                    enumerate(h.right.avl.root, rect);
                }
                queryL(h.left, rect);
        } else {
            if (h.right != null) {
                queryL(h.right, rect);
            } else {
                enumerate(h.avl.root, rect);
            }
        }
    }

    private void queryR(Node h, Interval2D rect) {
        if (h == null) {
            return;
        }
        if (h.right != null && rect.getIntervalX().getHigh() >= h.right.begin) {
            if (h.left != null) {
                enumerate(h.left.avl.root, rect);
            }
            queryR(h.right, rect);
        } else {
            if (h.left != null) {
                queryL(h.left, rect);
            } else {
                enumerate(h.avl.root, rect);
            }
        }
    }
    
    private void enumerate(AVLNode h, Interval2D rect) {
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
    
    private void enumerateL(AVLNode h, Interval2D rect) {
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

    private void enumerateR(AVLNode h, Interval2D rect) {
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
    
    private void addToAns(AVLNode h) {
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
    
    public Node getRoot(){
        return root;
    }
}
