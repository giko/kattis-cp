package toys;

import java.io.*;

public class Toys {
    public static int solve(int n, int k) {
        //F (n, k) = (F (n-1, k) + k)%n
        int n1 = 2;
        int result = 0;
        for (; n1 <= n; ++n1) {
            result = (result + k) % n1;
        }
        return result;
    }

    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = in.readLine().split(" ");

        int T = Integer.parseInt(parts[0]);
        int K = Integer.parseInt(parts[1]);
        out.println(solve(T, K));
        out.close();
    }
}
