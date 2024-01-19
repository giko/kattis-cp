package classy;

import java.io.*;
import java.util.*;

class classy {
    public static void main(String[] args) {
        FastIO io = new FastIO();

        int T = io.nextInt();
        for (int cI = 0; cI < T; ++cI) {
            int n = io.nextInt();
            TreeSet<Person> persons = new TreeSet<>();
            for (int pI = 0; pI < n; ++pI) {
                persons.add(new Person(io.next(), io.next()));
                io.next();
            }
            for (Person p : persons) {
                io.println(p.name);
            }
            io.println("==============================");
        }

        io.close();
    }
}

class Person implements Comparable<Person> {
    public final String name;
    public int[] classes;

    public Person(String name, String classes) {
        this.name = name.substring(0, name.length() - 1);
        String[] classesA = classes.split("-");
        this.classes = new int[classesA.length];
        for (int i = 0; i < classesA.length; ++i) {
            String clas = classesA[classesA.length - i - 1];
            if (clas.equals("upper")) {
                this.classes[i] = 1;
            } else if (clas.equals("lower")) {
                this.classes[i] = -1;
            } else {
                this.classes[i] = 0;
            }
        }
    }

    @Override
    public int compareTo(Person o) {
        int ml = Math.max(this.classes.length, o.classes.length);
        for (int i = 0; i < ml; ++i) {
            int cr = Integer.compare(
                    i >= o.classes.length ? 0 : o.classes[i],
                    i >= this.classes.length ? 0 : this.classes[i]
            );
            if (cr != 0) {
                return cr;
            }
        }
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name);
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