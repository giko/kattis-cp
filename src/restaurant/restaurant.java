package restaurant;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class restaurant {
    public static void main(String[] args) {
        FastIO io = new FastIO();

        while (true) {
            int N = io.nextInt();
            if (N == 0) {
                break;
            }

            Stack<AtomicInteger> p1 = new Stack<>();
            Stack<AtomicInteger> p2 = new Stack<>();

            for (int i = 0; i < N; ++i) {
                switch (io.next()) {
                    case "DROP":
                        int td = io.nextInt();

                        p2.push(new AtomicInteger(td));
                        io.println("DROP 2 " + td);
                        break;
                    case "TAKE":
                        int lt = 0;
                        int tt = io.nextInt();
                        while (tt != 0) {
                            if (p1.isEmpty()) {
                                if (lt != 0) {
                                    io.println("TAKE 1 " + lt);
                                    lt = 0;
                                }
                                int tm = 0;
                                while (!p2.isEmpty()) {
                                    tm += p2.peek().get();
                                    p1.push(p2.pop());
                                }
                                io.println("MOVE 2->1 " + tm);
                            }
                            AtomicInteger ti = p1.peek();
                            if (ti.get() <= tt) {
                                lt += ti.get();
                                tt -= ti.get();
                                p1.pop();
                            } else {
                                lt += tt;
                                ti.set(ti.get() - tt);
                                tt = 0;
                            }
                        }
                        if (lt != 0) {
                            io.println("TAKE 1 " + lt);
                        }
                        break;
                }
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