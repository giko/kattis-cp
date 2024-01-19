package bitbybit;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Solutions for <a href="https://open.kattis.com/problems/bitbybit">kattis bitbybit</a>
 * Uses 2 bitmasks and bitwise operators on them
 */
class bitbybit {
    protected static int known = 0;
    protected static int bits = 0;

    public static void main(String[] args) {
        FastIO io = new FastIO();
        while (true) {
            int n = io.nextInt();
            if (n == 0) {
                break;
            }
            known = 0;
            bits = 0;
            for (int i = 0; i < n; ++i) {
                String operator = io.next();
                switch (operator) {
                    case "SET": {
                        set(io.nextInt());
                        break;
                    }
                    case "CLEAR": {
                        clear(io.nextInt());
                        break;
                    }
                    case "AND": {
                        int bit1 = io.nextInt();
                        int bit2 = io.nextInt();
                        if ((known & (1 << bit1)) != 0 && (known & (1 << bit2)) != 0) {
                            if ((bits & (1 << bit1)) != 0 && (bits & (1 << bit2)) != 0) {
                                set(bit1);
                            } else {
                                clear(bit1);
                            }
                            break;
                        }

                        if ((known & (1 << bit1)) != 0 && (bits & (1 << bit1)) == 0) {
                            clear(bit1);
                            break;
                        }
                        if ((known & (1 << bit2)) != 0 && (bits & (1 << bit2)) == 0) {
                            clear(bit1);
                            break;
                        }
                        forget(bit1);
                        break;
                    }
                    case "OR": {
                        int bit1 = io.nextInt();
                        int bit2 = io.nextInt();
                        if ((known & (1 << bit1)) != 0 && (known & (1 << bit2)) != 0) {
                            if ((bits & (1 << bit1)) != 0 || (bits & (1 << bit2)) != 0) {
                                set(bit1);
                            } else {
                                clear(bit1);
                            }
                            break;
                        }

                        if ((known & (1 << bit1)) != 0 && (bits & (1 << bit1)) != 0) {
                            set(bit1);
                            break;
                        }
                        if ((known & (1 << bit2)) != 0 && (bits & (1 << bit2)) != 0) {
                            set(bit1);
                            break;
                        }
                        forget(bit1);

                        break;
                    }
                }
            }
            for (int i = 31; i >= 0; --i) {
                if ((known & (1 << i)) != 0) {
                    io.print((bits & (1 << i)) == 0 ? '0' : '1');
                } else {
                    io.print('?');
                }
            }
            io.println();
        }

        io.close();
    }

    public static void set(int bit) {
        known = known | (1 << bit);
        bits = bits | (1 << bit);
    }

    public static void clear(int bit) {
        known = known | (1 << bit);
        bits = bits & ~(1 << bit);
    }
    public static void forget(int bit) {
        known = known & ~(1 << bit);
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