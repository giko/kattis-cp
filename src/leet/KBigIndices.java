package leet;

import java.util.Comparator;

class Solution {
    public int kBigIndices(int[] nums, int k) {
        if (nums.length < 2 * k + 1) {
            return 0;
        }
        AVLTree<Integer> left = new AVLTree<>();
        AVLTree<Integer> right = new AVLTree<>();
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                left.add(nums[i]);
            } else {
                right.add(nums[i]);
            }
        }
        int ans = 0;
        for (int i = k; i < nums.length - k; i++) {
            int num = nums[i];
            right.remove(num);
            int leftKBig = left.select(k - 1);
            if (num > leftKBig && num > right.select(k - 1)) {
                ++ans;
                continue;
            }
            if (num < leftKBig) {
                left.add(num);
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.kBigIndices(new int[]{20, 9, 15, 15, 7, 14, 3, 6, 7, 5, 19, 16, 13, 17, 2, 5, 8, 4, 11, 12}, 9));
    }
}


class AVLTree<T extends Comparable<T>> {
    private class Node {
        T key;
        int height;
        int size; // size of the subtree rooted at this node
        Node left, right;

        public Node(T key) {
            this.key = key;
            this.height = 1; // new node is initially added at leaf
            this.size = 1;
        }
    }

    private Node root;
    private Comparator<T> comparator;

    public AVLTree() {
        this.comparator = Comparator.naturalOrder();
    }

    public void add(T key) {
        root = add(root, key);
    }

    private Node add(Node node, T key) {
        if (node == null) {
            return new Node(key);
        }

        int cmp = comparator.compare(key, node.key);
        if (cmp <= 0) {
            // Insert duplicates to the right
            node.left = add(node.left, key);
        } else {
            node.right = add(node.right, key);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        return balance(node);
    }


    public void remove(T key) {
        root = remove(root, key);
    }

    private Node remove(Node node, T key) {
        if (node == null) {
            return null;
        }

        int cmp = comparator.compare(key, node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = remove(node.right, mostLeftChild.key);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        return balance(node);
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node balance(Node node) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.size = 1 + size(y.left) + size(y.right);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.size = 1 + size(x.left) + size(x.right);
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.size = 1 + size(y.left) + size(y.right);

        return y;
    }

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int size(Node node) {
        return (node == null) ? 0 : node.size;
    }

    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    public T select(int rank) {
        if (rank < 0 || rank >= size(root)) {
            throw new IllegalArgumentException("Rank out of bounds");
        }
        return select(root, rank);
    }

    private T select(Node node, int rank) {
        if (node == null) {
            return null;
        }

        int leftSize = size(node.left);
        if (leftSize > rank) {
            return select(node.left, rank);
        } else if (leftSize < rank) {
            return select(node.right, rank - leftSize - 1);
        } else {
            return node.key;
        }
    }

    public int rank(T key) {
        return rank(root, key);
    }

    private int rank(Node node, T key) {
        if (node == null) {
            return 0;
        }

        int cmp = comparator.compare(key, node.key);
        if (cmp < 0) {
            return rank(node.left, key);
        } else if (cmp > 0) {
            return 1 + size(node.left) + rank(node.right, key);
        } else {
            return size(node.left);
        }
    }
}

