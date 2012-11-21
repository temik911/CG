package rangetreevisual;

public class AVLNode {
    AVLNode(Point p)
    {
        value = p;
        left = null;
        right = null;
        height = 0;
    }
    Point value;
    int height;
    AVLNode left;
    AVLNode right;
}