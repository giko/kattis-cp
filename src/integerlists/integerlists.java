package integerlists;

import java.io.*;
import java.util.Deque;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;

public class integerlists {
    public static void main(String[] args) {
        FastIO io = new FastIO();

        int N = io.nextInt();
        outter:
        for (int i = 0; i < N; ++i) {
            if (i % 10 == 0) {
                io.flush();
            }
            Deque<Byte> numbers = new LinkedList<>();
            boolean reversed = false;
            char[] commands = io.next().toCharArray();
            int n = io.nextInt();

            if (n == 0) {
                io.next();
            } else {
                io.nextByte();
                for (int j = 0; j < n; j++) {
                    numbers.add((byte) io.nextInt());
                }
            }
            for (char c : commands) {
                if (c == 'R') {
                    reversed = !reversed;
                } else {
                    if (numbers.isEmpty()) {
                        io.println("error");
                        continue outter;
                    }
                    if (reversed) {
                        numbers.removeLast();
                    } else {
                        numbers.removeFirst();
                    }
                }
            }
            if (numbers.isEmpty()) {
                io.println("[]");
                continue;
            }

            Iterator<Byte> it = reversed ? numbers.descendingIterator() : numbers.iterator();
            io.print("[" + it.next());
            while (it.hasNext()) {
                io.print("," + it.next());
            }
            io.println("]");
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
    public int nextByte() {
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
        } while (c > ' ' && c != ',' && c != ']');
        return res * sgn;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
}
