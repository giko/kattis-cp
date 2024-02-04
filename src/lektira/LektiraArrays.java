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
        for (int i = 0; i < wordChars.length - 2; ++i) {
            for (int j = i + 1; j < wordChars.length - 1; ++j) {
                if (compareIJs(wordChars, i, j, bestI, bestJ) < 0) {
                    bestI = i;
                    bestJ = j;
                }
            }
        }
        out.println(reverse(wordChars, bestI, bestJ));
        out.close();
    }

    public static int compareIJs(char[] word, int i1, int j1, int i2, int j2) {
        int li = word.length - 1;
        int cp = i1 == i2 ? i1 : 0;
        int rp = j1 == j2 ? j1 : li;
        for (; cp <= rp; cp++) {
            char c1 = word[charPosition(li, i1, j1, cp)];
            char c2 = word[charPosition(li, i2, j2, cp)];
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return 0;
    }

    public static int charPosition(int li, int i, int j, int cp) {
        return i >= cp ? i - cp : j >= cp ? j - (cp - i - 1) : li - (cp - j - 1);
    }

    public static char[] reverse(char[] word, int i, int j) {
        char[] newWord = new char[word.length];
        int li = word.length - 1;
        for (int cp = 0; cp < word.length; ++cp) {
            newWord[cp] = word[charPosition(li, i, j, cp)];
        }
        return newWord;
    }
}
