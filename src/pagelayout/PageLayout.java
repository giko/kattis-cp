package pagelayout;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

public class PageLayout {
    public static void main(String[] a) throws IOException {
        FastIO io = new FastIO();
        int n = 0;
        while ((n = io.nextInt()) != 0) {
            Rectangle[] stories = new Rectangle[n];
            for (int i = 0; i < n; i++) {
                stories[i] = new Rectangle(io.nextInt(), io.nextInt(), io.nextInt(), io.nextInt());
            }
            Solver solver = new Solver(stories);
            io.println(solver.maxArea);
        }
        io.close();
    }
}

class Solver {
    final int n;
    private final int[] intersections;

    private final Rectangle[] stories;
    int maxArea = 0;

    public Solver(Rectangle[] stories) {
        Arrays.sort(stories);
        this.stories = stories;
        this.n = stories.length;
        this.intersections = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = i; j < this.n; j++) {
                boolean result = stories[i].hasIntersections(stories[j]);
                if (result) {
                    intersections[i] |= 1 << j;
                    intersections[j] |= 1 << i;
                }
            }
        }
        solve(0, 0, 0);
    }

    private void solve(int currentIndex, int current, int currentArea) {
        if (currentArea > this.maxArea) {
            this.maxArea = currentArea;
        }

        if (currentIndex == n) {
            return;
        }

        solve(currentIndex + 1, current, currentArea);
        if ((intersections[currentIndex] & current) == 0) {
            solve(currentIndex + 1, current | 1 << currentIndex, currentArea + stories[currentIndex].area);
        }
    }
}

class Rectangle implements Comparable<Rectangle> {
    private int width;
    private int height;
    private int x;
    private int y;
    int area;

    public Rectangle(int w, int h, int x, int y) {
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        this.area = w * h;
    }

    public boolean hasIntersections(Rectangle o) {
        if (this.area == 0 || o.area == 0) {
            return true;
        }
        if (this.x + this.width <= o.x || o.x + o.width <= this.x) {
            return false;
        }
        if (this.y + this.height <= o.y || o.y + o.height <= this.y) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Rectangle o) {
        return Integer.compare(this.area, o.area);
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