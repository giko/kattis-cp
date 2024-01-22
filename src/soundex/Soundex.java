package soundex;

import java.io.*;

class Soundex {
    public final static int[] letterMap;

    static {
        letterMap = new int[26];

        assignLetters(new char[]{'B', 'F', 'P', 'V'}, 1);
        assignLetters(new char[]{'C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z'}, 2);
        assignLetters(new char[]{'D', 'T'}, 3);
        assignLetters(new char[]{'L'}, 4);
        assignLetters(new char[]{'M', 'N'}, 5);
        assignLetters(new char[]{'R'}, 6);
    }

    public static void assignLetters(char[] chars, int digit) {
        for (char c : chars) {
            letterMap[c - 'A'] = digit;
        }
    }

    public static String translate(String toTranslate) {
        StringBuilder sb = new StringBuilder();
        int lastDigit = 0;
        for (char c : toTranslate.toCharArray()) {
            if (letterMap[c - 'A'] != lastDigit && letterMap[c - 'A'] != 0) {
                sb.append(letterMap[c - 'A']);
            }
            lastDigit = letterMap[c - 'A'];
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = in.readLine()) != null) {
            out.println(translate(line));
        }

        out.close();
    }
}
