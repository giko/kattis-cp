package candydivision;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

class CandyDivision {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Set<Long> divisors = new TreeSet<>();
        long n = Long.parseLong(in.readLine());
        divisors.add(1L);
        divisors.add(n);
        for (long i = 2; i <= Math.sqrt(n); ++i) {
            if (n % i == 0) {
                divisors.add(i);
                divisors.add(n / i);
            }
        }
        for (Long i : divisors) {
            out.print((i - 1) + " ");
        }
        out.close();
    }
}
