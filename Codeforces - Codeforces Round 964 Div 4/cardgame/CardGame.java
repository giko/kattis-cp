package cardgame;//  B. Card Game

import java.util.*;
import java.lang.*;
import java.io.*;

public class CardGame {
    public static void main(String[] args) {
        var io = new FastIO();
        int t = io.nextInt();

        for (int caseIdx = 0; caseIdx < t; ++caseIdx) {
            int a1 = io.nextInt();
            int a2 = io.nextInt();
            int b1 = io.nextInt();
            int b2 = io.nextInt();

            int[][] combinations = {
                    {a1, b1, a2, b2},
                    {a1, b2, a2, b1},
                    {a2, b1, a1, b2},
                    {a2, b2, a1, b1}
            };
            int ways = 0;

            for (int[] combination : combinations) {
                int wins = 0;
                int loss = 0;
                if (combination[0] > combination[1]) {
                    ++wins;
                } else if(combination[0] < combination[1]) {
                    ++loss;
                }
                if (combination[2] > combination[3]) {
                    ++wins;
                } else if(combination[2] < combination[3]) {
                    ++loss;
                }
                if (wins > loss) {
                    ++ways;
                }
            }
            io.println(ways);
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