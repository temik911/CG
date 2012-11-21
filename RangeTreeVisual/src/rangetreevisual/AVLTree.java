package rangetreevisual;

public class AVLTree // public class
{

    public AVLTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return size;
    }
    
    public int height(AVLNode cur) {
        return cur == null ? -1 : cur.height;
    }

    public AVLNode insert(AVLNode cur, Point p) {
        if (cur == null) {
            cur = new AVLNode(p);
            return cur;
        }
        int compareResult = p.compareTo(cur.value);
        if (compareResult < 0) {
            cur.left = insert(cur.left, p);
        } else {
            if (compareResult > 0) {
                cur.right = insert(cur.right, p);
            }
        }
        if (height(cur.left) - height(cur.right) == 2) {
            if (height(cur.left.right) <= height(cur.left.left)) {
                cur = singleRotation(cur, 0);
            } else {
                cur = doubleRotation(cur, 0);
            }
        }
        
        if (height(cur.right) - height(cur.left) == 2) {
            if (height(cur.right.left) <= height(cur.right.right)) {
                cur = singleRotation(cur, 1);
            } else {
                cur = doubleRotation(cur, 1);
            }
        }
        cur.height = Math.max(height(cur.left), height(cur.right)) + 1;
        return cur;
    }
    
    private AVLNode singleLeftRotation(AVLNode node) {
        AVLNode temp = node.right;
        temp.left = node;
        temp.left.right = node.right.left;
        return temp;
    }

    private AVLNode singleRotation(AVLNode node, int side) {
        AVLNode temp = node; 
        if (side == 0) {
            temp = node.left;
            node.left = temp.right;
            temp.right = node;
            temp.right.height = Math.max(height(temp.right.left), height(temp.right.right)) + 1;
        } else {
            if (side == 1) {
                temp = node.right;
                node.right = temp.left;
                temp.left = node;
                temp.left.height = Math.max(height(temp.left.left), height(temp.left.right)) + 1;
            }
        }
        return temp;
    }

    private AVLNode doubleRotation(AVLNode node, int side) {
        if (side == 0) {
            node.left = singleRotation(node.left, 1);
            return singleRotation(node, 0);
        } else {
            if (side == 1) {
                node.right = singleRotation(node.right, 0);
                return singleRotation(node, 1);
            }
        }
        return node;
    }
    
    private int size;
    private int side;
    AVLNode root;
}