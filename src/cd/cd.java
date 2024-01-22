package cd;

import java.io.*;
import java.util.HashSet;

public class cd {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String caseLine;
        while (!(caseLine = in.readLine()).equals("0 0")) {
            String[] sl = caseLine.split(" ");
            int N = Integer.parseInt(sl[0]);
            int M = Integer.parseInt(sl[1]);
            HashSet<Integer> jackDisks = new HashSet<>(N * 2);
            int bothOwn = 0;
            for (int i = 0; i < N; ++i) {
                jackDisks.add(Integer.valueOf(in.readLine()));
            }
            for (int i = 0; i < M; ++i) {
                if (jackDisks.contains(Integer.valueOf(in.readLine()))) {
                    ++bothOwn;
                }
            }
            out.println(bothOwn);
        }
        out.close();
    }
}
