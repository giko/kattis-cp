package froshweek2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FroshWeek2 {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] nmParts = in.readLine().split(" ");
        int n = Integer.parseInt(nmParts[0]);
        int m = Integer.parseInt(nmParts[1]);

        int[] tasksLengths = new int[n];
        int[] quitePeriodsLengths = new int[m];

        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 0; i < n; ++i) {
            tasksLengths[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < m; ++i) {
            quitePeriodsLengths[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(tasksLengths);
        Arrays.sort(quitePeriodsLengths);

        int result = 0;
        int tIdx = n - 1;
        int pIdx = m - 1;
        while (tIdx >= 0 && pIdx >= 0) {
            if (tasksLengths[tIdx] <= quitePeriodsLengths[pIdx]) {
                result++;
                tIdx--;
                pIdx--;
            } else {
                tIdx--;
            }
        }

        out.println(result);

        out.close();
    }
}