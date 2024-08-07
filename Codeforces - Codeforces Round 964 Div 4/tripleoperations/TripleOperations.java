package tripleoperations;//  E. Triple Operations

import java.util.*;
import java.lang.*;
import java.io.*;

public class TripleOperations {
    public static int[] dp = new int[200001];
    public static int[] prefixSum = new int[200001];

    static {
        for (int num = 1; num <= 200_000; num++) {
            howManyTimesTillZero(num);
        }
        prefixSum[1] = dp[1];
        for (int i = 2; i <= 200_000; i++) {
            prefixSum[i] = prefixSum[i - 1] + dp[i];
        }
    }

    public static void main(String[] args) {
        var io = new FastIO();
        int t = io.nextInt();
        for (int caseIdx = 0; caseIdx < t; caseIdx++) {
            int l = io.nextInt();
            int r = io.nextInt();
            int numsToFirstZero = howManyTimesTillZero(l);
            int operations = numsToFirstZero + prefixSum[r] - prefixSum[l - 1];
            io.println(operations);
        }
        io.close();
    }

    public static int howManyTimesTillZero(int num) {
        if (dp[num] != 0) {
            return dp[num];
        }
        int times = 0;
        int currentNum = num;
        while (currentNum > 0) {
            currentNum /= 3;
            ++times;
            if (dp[currentNum] != 0) {
                times += dp[currentNum];
                dp[num] = times;
                break;
            }
        }
        dp[num] = times;
        return times;
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