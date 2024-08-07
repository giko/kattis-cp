package showering;//  C. Showering

import java.util.*;
import java.lang.*;
import java.io.*;

public class Showering {
    public static void main(String[] args) {
        var io = new FastIO();
        int t = io.nextInt();
        outer: for (int caseIdx = 0; caseIdx < t; ++caseIdx) {
            int n = io.nextInt();
            int s = io.nextInt();
            int m = io.nextInt();
            int[][] intervals = new int[n][2];
            for (int intervalIdx = 0; intervalIdx < n; ++intervalIdx) {
                intervals[intervalIdx][0] = io.nextInt();
                intervals[intervalIdx][1] = io.nextInt();
            }
            if (intervals[0][0] >= s) {
                io.println("YES");
                continue;
            }
            if (intervals[n - 1][1] <= m - s) {
                io.println("YES");
                continue;
            }
            for (int i = 1; i < n; ++i) {
                if (intervals[i][0] - intervals[i - 1][1] >= s) {
                    io.println("YES");
                    continue outer;
                }
            }
            io.println("NO");
        }
        io.close();
    }
}

class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    public FastIO() {
        this(System.in, System.out);
    }

    public FastIO(InputStream i, OutputStream o) {
        super(o);
        stream = i;
    }

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