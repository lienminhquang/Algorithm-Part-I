
public class LLRedBlackTree<T>{
    /**
     * Node
     */
    public static boolean RED = true;
    public static boolean BLACK = false;
    public class Node {
        T value;
        int key;
        Node left;
        Node right;
        boolean color = RED;
        public Node(int _key, T _value, boolean _color) {
            key = _key;
            value = _value;
            color = _color;
            left = null;
            right = null;
        }
    }

    private Node root;
    public LLRedBlackTree() {
        root = null;
    }
    private boolean isRed(Node node) {
        if(node == null) return false;
        return node.color == RED;
    }

    public void put(int key, T value) {
        root = _putt(root, key, value);
    }
    private Node _putt(Node node, int key, T value) {
        if(node == null) return new Node(key, value, RED);
        if(key < node.key) node.left = _putt(node.left, key, value);
        else if(key > node.key) node.right = _putt(node.right, key, value);
        else node.value = value;

        if(isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if(isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if(isRed(node.right) && isRed(node.left)) {
            node.color = node == root ? BLACK : RED;
            node.right.color = BLACK;
            node.left.color = BLACK;
        }
        return node;
    }
    public T get(int key) {
        Node current = root;
        while(current != null)
        {
            if(current.key == key) return current.value;
            if(current.key < key) {
                current = current.right;
            }
            else {
                current = current.left;
            }
        }
        return null;
    }

    public void delete(int key) {
        root = _delete(key, root);
    }

    private Node _delete(int key, Node node) {
        return null;
    }

    private Node rotateLeft(Node node)
    {
        Node rs = node.right;
        node.right = rs.left;
        rs.left = node;
        rs.color = BLACK;
        node.color = RED;
        return rs;
    }
    private Node rotateRight(Node node)
    {
        Node rs = node.left;
        node.left = rs.right;
        rs.right = node;
        rs.color = BLACK;
        node.color = RED;
        return rs;
    }

}
