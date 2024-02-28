package airconditioned;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class Airconditioned {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        int[][] minionsPreferences = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] parts = in.readLine().split(" ");
            minionsPreferences[i][0] = Integer.parseInt(parts[0]);
            minionsPreferences[i][1] = Integer.parseInt(parts[1]);
        }
        Arrays.sort(minionsPreferences, Comparator.comparingInt(o -> o[1]));

        int previousRoomTemp = Integer.MIN_VALUE;
        int roomsCount = 0;
        for (int i = 0; i < n; i++) {
            if (minionsPreferences[i][0] <= previousRoomTemp) {
                continue;
            }
            previousRoomTemp = minionsPreferences[i][1];
            roomsCount++;
        }

        out.println(roomsCount);

        out.close();
    }
}