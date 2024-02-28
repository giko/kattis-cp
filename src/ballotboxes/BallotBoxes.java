package ballotboxes;

import java.io.*;
import java.util.InputMismatchException;
import java.util.PriorityQueue;

public class BallotBoxes {
    public static void main(String[] a) throws IOException {
        FastIO io = new FastIO();

        int citiesCount;
        while ((citiesCount = io.nextInt()) != -1) {
            int boxes = io.nextInt() - citiesCount;
            PriorityQueue<int[]> pq = new PriorityQueue<>(citiesCount,
                    (c1, c2) -> Integer.compare(c2[2], c1[2]));
            for (int i = 0; i < citiesCount; i++) {
                int population = io.nextInt();
                pq.add(new int[]{population, 1, population});
            }
            while (boxes-- > 0) {
                int[] city = pq.poll();
                city[1]++;
                city[2] = ceilDiv(city[0], city[1]);
                pq.add(city);
            }

            io.println(pq.peek()[2]);
        }

        io.close();
    }

    public static int ceilDiv(int a, int b) {
        return (int) Math.ceil((double) a / b);
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