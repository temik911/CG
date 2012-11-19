package rangetreevisual;

public class SecondaryTree {
    public class Node{
        Point value;                   
        Node left;
        Node right;

        Node(Point p) {
            value = p;
            left = null;
            right = null;
        }
    }
    public Node root;
    
    public void put(Point p) {
        root = put(root, p);
    }
    
    private Node put(Node cur, Point p) {
        if (cur == null) {
            Node r = new Node(p);
            return r;
        }
        if (p.getY() <= cur.value.getY()) {
            cur.left  = put(cur.left,  p);
        } else {
            cur.right = put(cur.right, p);
        }
        return cur;
    }
}
