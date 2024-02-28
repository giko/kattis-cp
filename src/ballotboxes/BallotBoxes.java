package ballotboxes;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.PriorityQueue;

/**
 * BallotBoxes kattis O((B-N)*log(N)) solutions
 * uses heapify
 */
public class BallotBoxes {
    public static void main(String[] a) throws IOException {
        FastIO io = new FastIO();

        int citiesCount;
        while ((citiesCount = io.nextInt()) != -1) {
            int boxes = io.nextInt() - citiesCount;
            List<City> cities = new ArrayList<>(citiesCount);
            for (int i = 0; i < citiesCount; i++) {
                cities.add(new City(io.nextInt()));
            }
            //uses heapify, so it's O(N)
            PriorityQueue<City> pq = new PriorityQueue<>(cities);
            // (B-N)*log(N)
            while (boxes-- > 0) {
                City city = pq.poll();
                city.increaseBoxes();
                pq.add(city);
            }

            io.println(pq.peek().populationPerBox);
        }

        io.close();
    }

}

class City implements Comparable<City> {
    int population;
    private int boxes = 1;
    int populationPerBox;

    public City(int population) {
        this.population = population;
        this.populationPerBox = population;
    }

    @Override
    public int compareTo(City o) {
        return Integer.compare(o.populationPerBox, this.populationPerBox);
    }

    public void increaseBoxes() {
        this.boxes++;
        this.populationPerBox = ceilDiv(this.population, this.boxes);
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