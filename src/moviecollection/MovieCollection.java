package moviecollection;

import java.io.*;
import java.util.InputMismatchException;

class MovieCollection {
    public static void main(String[] args) {
        FastIO io = new FastIO();
        int cases = io.nextInt();
        for (int cIdx = 0; cIdx < cases; cIdx++) {
            int m = io.nextInt();
            int r = io.nextInt();
            int[] idXMap = new int[m + 1];
            FenwickTree ft = new FenwickTree(m + r + 1);
            for (int i = 0; i < m; ++i) {
                ft.update(i + r, 1);
                idXMap[i + 1] = i + r;
            }
            for (int i = 1; i <= r; ++i) {
                int mIdx = io.nextInt();
                if (idXMap[mIdx] - 1 > 0) {
                    io.print(ft.rsq(idXMap[mIdx] - 1) + " ");
                } else {
                    io.print("0 ");
                }
                ft.update(idXMap[mIdx], -1);
                idXMap[mIdx] = r - i;
                ft.update(idXMap[mIdx], 1);
            }
            io.println();
        }

        io.close();
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