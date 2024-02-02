package golombrulers;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GolombRulers {

    public static final String MISSING = "missing";

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        boolean[] bs = new boolean[2001];
        outer:
        while ((line = in.readLine()) != null) {
            Arrays.fill(bs, false);
            List<Integer> values = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            int n = Collections.max(values);
            bs[0] = true;
            for (int i = 0; i < values.size() - 1; i++) {
                for (int j = i + 1; j < values.size(); j++) {
                    int res = Math.abs(values.get(i) - values.get(j));
                    if (res > n) {
                        continue;
                    }
                    if (bs[res]) {
                        out.println("not a ruler");
                        continue outer;
                    }
                    bs[res] = true;
                }
            }
            StringBuilder missingSb = new StringBuilder(MISSING);
            for (int i = 0; i <= n; i++) {
                if (!bs[i]) {
                    missingSb.append(" ").append(i);
                }
            }
            BitSet b = new BitSet(1000);
            if (missingSb.toString().equals(MISSING)) {
                out.println("perfect");
            } else {
                out.println(missingSb);
            }
        }

        out.close();
    }
}
