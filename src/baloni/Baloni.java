package baloni;

import java.util.*;
import java.io.*;

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null) try {
            while (st == null || !st.hasMoreTokens()) {
                line = r.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            token = st.nextToken();
        } catch (IOException e) {
        }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

class Baloni {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int[] hyst = new int[1_000_000];
        int result = 0;

        int n = io.getInt();
        for (int i = 0; i < n; ++i) {
            int h = io.getInt();
            if (hyst[h] == 0) {
                ++result;
            } else {
                --hyst[h];
            }
            ++hyst[h-1];
        }

        io.println(result);
        io.flush();
    }
}