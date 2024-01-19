package imageprocessing;

import java.io.*;
import java.util.InputMismatchException;

class ImageProcessing {
    public static void main(String[] args) {
        FastIO io = new FastIO();
        int H = io.nextInt();
        int W = io.nextInt();
        int N = io.nextInt();
        int M = io.nextInt();

        int[][] im = new int[H][W];
        int[][] ke = new int[N][M];

        for (int i=0; i < H; ++i) {
            for (int j=0; j < W; ++j) {
                im[i][j] = io.nextInt();
            }
        }
        for (int i=0; i < N; ++i) {
            for (int j=0; j < M; ++j) {
                ke[N-i-1][M-j-1] = io.nextInt();
            }
        }

        for (int i=0; i <= H-N; ++i) {
            for (int j=0; j <= W-M; ++j) {
                int sum = 0;
                for (int ki=0; ki < N; ++ki) {
                    for (int kj = 0; kj < M; ++kj) {
                        sum += im[i+ki][j+kj] * ke[ki][kj];
                    }
                }
                io.print(sum);
                io.print(" ");
            }
            io.println();
        }

        io.close();
    }
}

class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    // standard input
    public FastIO() { this(System.in, System.out); }

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
        if (numChars == -1) { throw new InputMismatchException(); }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) { throw new InputMismatchException(); }
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
        do { c = nextByte(); } while (c <= ' ');

        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = nextByte();
        } while (c > ' ');
        return res.toString();
    }

    public int nextInt() {  // nextLong() would be implemented similarly
        int c;
        do { c = nextByte(); } while (c <= ' ');

        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = nextByte();
        }

        int res = 0;
        do {
            if (c < '0' || c > '9') { throw new InputMismatchException(); }
            res = 10 * res + c - '0';
            c = nextByte();
        } while (c > ' ');
        return res * sgn;
    }

    public double nextDouble() { return Double.parseDouble(next()); }
}