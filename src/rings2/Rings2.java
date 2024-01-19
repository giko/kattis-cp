package rings2;

import java.io.*;
import java.util.*;

class Rings2 {
    public static void main(String[] args) {
        FastIO io = new FastIO();
        int n = io.nextInt();
        int m = io.nextInt();
        int[][] grid = new int[n + 2][m + 2];
        List<Tuple> toCheck = new LinkedList<>();
        for (int i = 1; i < n + 1; ++i) {
            int j = 1;
            for (char c : io.next().toCharArray()) {
                if (c == 'T') {
                    grid[i][j] = 1;
                    toCheck.add(new Tuple(i, j));
                }
                ++j;
            }
        }

        int rings = 0;

        while (!toCheck.isEmpty()) {
            Iterator<Tuple> i = toCheck.iterator();
            while (i.hasNext()) {
                Tuple c = i.next();
                int cR = grid[c.r][c.c];
                if (grid[c.r + 1][c.c] >= cR
                        && grid[c.r - 1][c.c] >= cR
                        && grid[c.r][c.c + 1] >= cR
                        && grid[c.r][c.c - 1] >= cR
                ) {
                    ++grid[c.r][c.c];
                    rings = Math.max(rings, grid[c.r][c.c]);
                } else {
                    i.remove();
                }
            }
        }

        for (int i = 1; i < n + 1; ++i) {
            for (int j = 1; j < m + 1; ++j) {
                int v = grid[i][j];
                if (rings < 10) {
                    io.print('.');
                } else {
                    if (v < 100) {
                        io.print('.');
                        if (v < 10) {
                            io.print('.');
                        }
                    }
                }
                if (v == 0) {
                    io.print('.');
                } else {
                    io.print(grid[i][j]);
                }
            }
            io.println();
        }

        io.close();
    }
}

class Tuple {
    public int r, c;

    public Tuple(int r, int c) {
        this.r = r;
        this.c = c;
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