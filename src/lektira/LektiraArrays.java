package lektira;

import java.io.*;

public class LektiraArrays {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String word = in.readLine();
        char[] wordChars = word.toCharArray();

        int bestI = 0;
        int bestJ = 1;
        for (int i = 0; i < word.length() - 2; i++) {
            for (int j = 2; j < word.length() - 1; j++) {
                if (compareIJs(wordChars, i, j, bestI, bestJ) < 0) {
                    bestI = i;
                    bestJ = j;
                }
            }
        }
        out.println(reverse(word.substring(0, bestI + 1))
                + reverse(word.substring(bestI + 1, bestJ + 1))
                + reverse(word.substring(bestJ + 1)));
        out.close();
    }

    public static int compareIJs(char[] word, int i1, int j1, int i2, int j2) {
        int li = word.length - 1;
        for (int cp = 0; cp <= li; cp++) {
            char c1 = word[charPosition(li, i1, j1, cp)];
            char c2 = word[charPosition(li, i2, j2, cp)];
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return 0;
    }

    public static int charPosition(int li, int i, int j, int cp) {
        if (i >= cp) {
            return i - cp;
        } else {
            if (j >= cp) {
                return j - (cp - i - 1);
            } else {
                return li - (cp - j - 1);
            }
        }
    }

    public static String reverse(String a) {
        return new StringBuilder(a).reverse().toString();
    }
}
