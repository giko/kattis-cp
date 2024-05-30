package increasingsubsequence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class IncreasingSubsequence {
    public static void main(String[] a) throws IOException {
        FastIO io = new FastIO();
        int N;
        while ((N = io.nextInt()) != 0) {
            int[] nums = new int[N];
            for (int i=0; i<N; ++i) {
                nums[i] = io.nextInt();
            }

            int[] dp = new int[N];
            int[] predecessors = new int[N];
            int[] indices = new int[N];
            Arrays.fill(predecessors, -1);

            int length = 0;

            for (int i = 0; i < N; i++) {
                int num = nums[i];
                int pos = Arrays.binarySearch(dp, 0, length, num);
                if (pos < 0) {
                    pos = -(pos + 1);
                }
                dp[pos] = num;
                indices[pos] = i;
                if (pos > 0) {
                    predecessors[i] = indices[pos - 1];
                }
                if (pos == length) {
                    length++;
                }
                io.println("dp: " + Arrays.toString(dp));
                io.println("pr: " + Arrays.toString(predecessors));
                io.println("id: " + Arrays.toString(indices));
                io.println('-');
            }

            int[] lis = new int[length];
            for (int i = indices[length - 1], k = length - 1; i >= 0; i = predecessors[i], k--) {
                lis[k] = nums[i];
            }

            io.print(length);
            io.print(' ');
            for (int num : lis) {
                io.print(num);
                io.print(' ');
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