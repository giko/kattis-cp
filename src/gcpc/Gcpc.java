package gcpc;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

class Gcpc {
    final AVLTree<Team> ost = new AVLTree<>();
    final Map<Integer, Team> idToTeam;
    final Team favTeam;

    public Gcpc(int n) {
        idToTeam = new HashMap<>(n);
        for (int i = 1; i <= n; i++) {
            Team t = new Team(i, 0, 0);
            ost.add(t);
            idToTeam.put(i, t);
        }
        favTeam = idToTeam.get(1);
    }

    public int updateScore(int teamId, int penalty) {
        Team t = idToTeam.get(teamId);
        ost.remove(t);
        t.update(penalty);
        ost.add(t);
        return ost.rank(favTeam) + 1;
    }


    public static void main(String[] args) {
        FastIO io = new FastIO();
        int n = io.nextInt();
        int m = io.nextInt();
        Gcpc gcpc = new Gcpc(n);
        for (int i = 0; i < m; i++) {
            io.println(gcpc.updateScore(io.nextInt(), io.nextInt()));
        }

        io.close();
    }
}

class Team implements Comparable<Team> {
    int score;
    int penalty;
    int id;

    public Team(int id, int score, int penalty) {
        this.id = id;
        this.score = score;
        this.penalty = penalty;
    }

    public void update(int penalty) {
        ++this.score;
        this.penalty += penalty;
    }

    @Override
    public int compareTo(Team o) {
        int res = Integer.compare(o.score, this.score);
        if (res == 0) {
            int penaltyRes = Integer.compare(this.penalty, o.penalty);
            if (penaltyRes == 0) {
                return Integer.compare(this.id, o.id);
            }
            return penaltyRes;
        }
        return res;
    }
}

class AVLTree<T extends Comparable<T>> {
    private class Node {
        T key;
        int height;
        int size; // Size of the subtree rooted with this node
        Node left, right;

        public Node(T key) {
            this.key = key;
            this.height = 1; // New node is initially added at leaf
            this.size = 1;
        }
    }

    private Node root;
    private Comparator<T> comparator;

    public AVLTree() {
        this.root = null;
        this.comparator = Comparator.naturalOrder();
    }

    // Get the height of the node
    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Get the size of the node
    private int size(Node N) {
        if (N == null)
            return 0;
        return N.size;
    }

    // Right rotate subtree rooted with y
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Update sizes
        y.size = size(y.left) + size(y.right) + 1;
        x.size = size(x.left) + size(x.right) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Update sizes
        x.size = size(x.left) + size(x.right) + 1;
        y.size = size(y.left) + size(y.right) + 1;

        // Return new root
        return y;
    }

    // Get balance factor of node N
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Insert a node
    public void add(T key) {
        root = addRecursive(root, key);
    }

    private Node addRecursive(Node node, T key) {
        // Perform the normal BST insertion
        if (node == null)
            return new Node(key);

        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            node.left = addRecursive(node.left, key);
        else if (cmp > 0)
            node.right = addRecursive(node.right, key);
        else // Duplicate keys not allowed
            return node;

        // Update height and size of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        // Get the balance factor of this ancestor node to check whether
        // this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && comparator.compare(key, node.left.key) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && comparator.compare(key, node.right.key) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && comparator.compare(key, node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && comparator.compare(key, node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // The function to remove a node
    public void remove(T key) {
        root = removeRecursive(root, key);
    }

    private Node removeRecursive(Node root, T key) {
        // Standard BST delete
        if (root == null)
            return root;

        int cmp = comparator.compare(key, root.key);
        if (cmp < 0)
            root.left = removeRecursive(root.left, key);
        else if (cmp > 0)
            root.right = removeRecursive(root.right, key);
        else {
            // Node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = removeRecursive(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // Update height of the current node
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        root.size = size(root.left) + size(root.right) + 1;

        // Get the balance factor of this node (to check whether this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    Node minValueNode(Node node) {
        Node current = node;

        // Loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }

    // Returns number of nodes in tree whose keys are less than the given key.
    public int rank(T key) {
        return rankRecursive(root, key);
    }

    private int rankRecursive(Node node, T key) {
        if (node == null)
            return 0;

        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            return rankRecursive(node.left, key);
        else if (cmp > 0)
            return size(node.left) + 1 + rankRecursive(node.right, key);

        return size(node.left);
    }

    // Main method for testing
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        // Example usage
        tree.add(10);
        tree.add(20);
        tree.add(30);
        tree.add(40);
        tree.add(50);
        tree.add(25);

        // Print rank of 30
        System.out.println("Rank of 30: " + tree.rank(30));
    }
}


class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    // standard input
    public FastIO() {
        this(System.in, System.out);
    }

    public FastIO(InputStream i, OutputStream o) {
        super(o);
        stream = i;
    }

    // file input
    public FastIO(String i, String o) throws IOException {
        super(new FileWriter(o));
        stream = new FileInputStream(i);
    }

    // throws InputMismatchException() if previously detected end of file
    private int nextByte() {
        if (numChars == -1) {
            throw new InputMismatchException();
        }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars == -1) {
                return -1;  // end of file
            }
        }
        return buf[curChar++];
    }

    // to read in entire lines, replace c <= ' '
    // with a function that checks whether c is a line break
    public String next() {
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');

        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = nextByte();
        } while (c > ' ');
        return res.toString();
    }

    public int nextInt() {  // nextLong() would be implemented similarly
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');

        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = nextByte();
        }

        int res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res = 10 * res + c - '0';
            c = nextByte();
        } while (c > ' ');
        return res * sgn;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
}