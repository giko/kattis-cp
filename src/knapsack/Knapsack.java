package knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Knapsack {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String sizes;
        while ((sizes = in.readLine()) != null) {
            var st = new StringTokenizer(sizes);
            var C = Integer.parseInt(st.nextToken());
            var n = Integer.parseInt(st.nextToken());
            int[] values = new int[n];
            int[] weights = new int[n];
            for (int i = 0; i < n; i++) {
                var st2 = new StringTokenizer(in.readLine());
                values[i] = Integer.parseInt(st2.nextToken());
                weights[i] = Integer.parseInt(st2.nextToken());
            }
            var result = KnapsackSolver.knapsack(weights, values, C);
            out.println(result.size());
            for (Integer itemIdx : result) {
                out.print(itemIdx);
                out.print(' ');
            }
            out.println();
        }

        out.close();
    }
}

class KnapsackSolver {
    public static List<Integer> knapsack(int[] weights, int[] values, int C) {
        int n = weights.length;
        int[][] dp = new int[n + 1][C + 1];

        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= C; c++) {
                if (weights[i] <= c) {
                    dp[i + 1][c] = Math.max(dp[i][c], dp[i][c - weights[i]] + values[i]);
                } else {
                    dp[i + 1][c] = dp[i][c];
                }
            }
        }

        // Restore the sequence of items
        List<Integer> selectedItems = new ArrayList<>();
        int c = C;
        for (int i = n; i > 0 && c > 0; i--) {
            if (dp[i][c] != dp[i - 1][c]) {
                selectedItems.add(i - 1); // Item i-1 was included
                c -= weights[i - 1];
            }
        }
        return selectedItems;
    }
}
