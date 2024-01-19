import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

class basicprogramming2 {
    public static void main(String[] args) {
        FastIO io = new FastIO();
        int N = io.nextInt();
        int t = io.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; ++i) {
            a[i] = io.nextInt();
        }
        Arrays.sort(a);
        sw:
        switch (t) {
            case 1: {
                int li = 0;
                int ri = N - 1;
                while (li < ri) {
                    int liV = a[li];
                    int riV = a[ri];
                    if (liV + riV == 7777 && liV != riV) {
                        io.println("Yes");
                        break sw;
                    }
                    if (liV + riV > 7777) {
                        --ri;
                    }
                    if (liV + riV < 7777) {
                        ++li;
                    }
                }
                io.println("No");
                break;
            }
            case 2:
                for (int i = 1; i < N; ++i) {
                    if (a[i] == a[i - 1]) {
                        io.println("Contains duplicate");
                        break sw;
                    }
                }
                io.println("Unique");
                break;
            case 3:
                int mV = a[N / 2];
                int li = N / 2;
                int ri = N / 2;
                while (li >= 1 && a[li - 1] == mV) {
                    --li;
                }
                while (ri < N - 1 && a[ri + 1] == mV) {
                    ++ri;
                }

                if (ri - li + 1 > N / 2) {
                    io.println(mV);
                } else {
                    io.println("-1");
                }

                break;
            case 4:
                if (N % 2 == 0) {
                    io.println(a[N / 2 - 1] + " " + a[N / 2]);
                } else {
                    io.println(a[N / 2]);
                }
                break;
            case 5:
                for (int i = 0; i < N; ++i) {
                    if (a[i] < 100) {
                        continue;
                    }
                    if (a[i] > 999) {
                        break;
                    }
                    io.print(a[i] + " ");
                }
                io.println();
                break;
            default:
                throw new IllegalArgumentException();
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