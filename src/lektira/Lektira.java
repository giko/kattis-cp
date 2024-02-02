package lektira;

import java.io.*;
import java.util.TreeSet;

public class Lektira {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String word = in.readLine();

        TreeSet<String> set = new TreeSet<>();
        for (int i = 1; i < word.length(); i++) {
            for (int j = i + 1; j < word.length(); j++) {
                set.add(
                        reverse(word.substring(0, i))
                                + reverse(word.substring(i, j))
                                + reverse(word.substring(j))
                );
            }
        }
        out.println(set.first());
        out.close();
    }

    public static String reverse(String a) {
        return new StringBuilder(a).reverse().toString();
    }
}
