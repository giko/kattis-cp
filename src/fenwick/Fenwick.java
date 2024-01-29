package fenwick;

import java.io.*;

class Fenwick {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] NQParts = in.readLine().split(" ");
        int N = Integer.parseInt(NQParts[0]);
        int Q = Integer.parseInt(NQParts[1]);
        FenwickTree ft = new FenwickTree(N + 1);
        for (int j = 0; j < Q; j++) {
            String[] commandParts = in.readLine().split(" ");
            if (commandParts[0].equals("+")) {
                ft.update(Integer.parseInt(commandParts[1]), Long.parseLong(commandParts[2]));
            } else {
                int i = Integer.parseInt(commandParts[1]);
                if (i == 0) {
                    out.println("0");
                } else {
                    out.println(ft.rsq(i - 1));
                }
            }
        }

        out.close();
    }
}

class FenwickTree {
    private long[] tree;

    public FenwickTree(int size) {
        tree = new long[size + 1];
    }

    public void update(int index, long value) {
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
