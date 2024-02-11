package paintings;

import java.io.*;
import java.util.*;

class Solver {
    int[] best = null;
    int sum = 0;
    final int n;
    final int[] blackList;
    private final int[] current;
    private final boolean[] used;

    public Solver(int n, int[] blackList) {
        this.n = n;
        this.blackList = blackList;
        this.current = new int[n];
        this.used = new boolean[n];
        solve(0);
    }

    private void solve(int currentIndex) {
        if (currentIndex == n) {
            ++sum;
            if (best == null) {
                best = Arrays.copyOf(current, n);
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (currentIndex > 0 && (blackList[current[currentIndex - 1]] & (1 << i)) != 0) {
                continue;
            }
            if (used[i]) {
                continue;
            }
            used[i] = true;
            current[currentIndex] = i;
            solve(currentIndex + 1);
            used[i] = false;
        }
    }
}

public class Paintings {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(in.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(in.readLine());
            String[] colors = in.readLine().split(" ");
            Map<String, Integer> colorToId = new HashMap<>();
            for (int i = 0; i < colors.length; i++) {
                colorToId.put(colors[i], i);
            }
            int M = Integer.parseInt(in.readLine());
            int[] blackList = new int[N];
            for (int m = 0; m < M; m++) {
                String[] parts = in.readLine().split(" ");
                blackList[colorToId.get(parts[0])] |= 1 << colorToId.get(parts[1]);
                blackList[colorToId.get(parts[1])] |= 1 << colorToId.get(parts[0]);
            }
            Solver solver = new Solver(N, blackList);
            out.println(solver.sum);
            for (int colorIndex : solver.best) {
                out.print(colors[colorIndex] + " ");
            }
            out.println();
        }

        out.close();
    }
}
