package firefly;

import java.io.*;

/**
 * Kattis firefly problem
 * O(nlogh + hlogh) = O((n+h)logh) solution
 */
public class FireFly {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = in.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int H = Integer.parseInt(parts[1]);

        FenwickTree stalagmitesFt = new FenwickTree(H + 1);
        FenwickTree stalactitesFt = new FenwickTree(H + 1);
        for (int i = 0; i < N / 2; i++) {
            stalagmitesFt.update(Integer.parseInt(in.readLine()), 1);
            stalactitesFt.update(Integer.parseInt(in.readLine()), 1);
        }

        int minHit = Integer.MAX_VALUE;
        int levels = 0;
        for (int level = 0; level < H; level++) {
            int hits = (int) (stalagmitesFt.rsq(level + 1, H) + stalactitesFt.rsq(H - level, H));
            if (hits < minHit) {
                minHit = hits;
                levels = 1;
            } else if (hits == minHit) {
                levels++;
            }
        }

        out.println(minHit + " " + levels);

        out.close();
    }
}

class FenwickTree {
    private int[] tree;

    public FenwickTree(int size) {
        tree = new int[size + 1];
    }

    public void update(int index, int value) {
        ++index;
        while (index < tree.length) {
            tree[index] += value;
            index += index & -index;
        }
    }

    public long rsq(int index) {
        ++index;
        long sum = 0;
        while (index > 0) {
            sum += tree[index];
            index -= index & -index;
        }
        return sum;
    }

    public long rsq(int i, int j) {
        return rsq(j) - rsq(i - 1);
    }
}