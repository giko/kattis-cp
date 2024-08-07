package slavicexam;//  D. Slavic's Exam

import java.util.*;
import java.lang.*;
import java.io.*;

public class SlavicExam {
    public static void main(String[] args) {
        var io = new FastIO();
        int T = io.nextInt();
        for (int caseIdx = 0; caseIdx < T; ++ caseIdx) {
            var s = io.next();
            var t = io.next();
            var sChars = s.toCharArray();
            var tChars = t.toCharArray();
            int sIdx = 0;
            int tIdx = 0;
            while (sIdx < sChars.length && tIdx < tChars.length) {
                if (sChars[sIdx] == tChars[tIdx]) {
                    ++sIdx;
                    ++tIdx;
                    continue;
                }
                if (sChars[sIdx] == '?') {
                    sChars[sIdx++] = tChars[tIdx++];
                    continue;
                }
                if (sChars[sIdx] != tChars[tIdx]) {
                    ++sIdx;
                }
            }
            if (tIdx != tChars.length) {
                io.println("NO");
                continue;
            }
            for (int i = sIdx; i < sChars.length; ++i) {
                if (sChars[i] == '?') {
                    sChars[i] = 'a';
                }
            }
            io.println("YES");
            io.println(new String(sChars));
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