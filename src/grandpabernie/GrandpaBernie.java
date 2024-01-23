package grandpabernie;

import java.io.*;
import java.util.*;

class GrandpaBernie {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // 195 countries in the world, let's set initial cap to 300 just in case
        Map<String, List<Integer>> countryYearsMap = new HashMap<>(300);

        int n = Integer.parseInt(in.readLine());
        for (int i = 0; i < n; i++) {
            String[] countryYear = in.readLine().split(" ");
            countryYearsMap.putIfAbsent(countryYear[0], new ArrayList<>());
            countryYearsMap.get(countryYear[0]).add(Integer.valueOf(countryYear[1]));
        }
        Set<String> sorted = new HashSet<>();
        int q = Integer.parseInt(in.readLine());
        for (int i = 0; i < q; i++) {
            String[] countryK = in.readLine().split(" ");

            if (!sorted.contains(countryK[0])) {
                countryYearsMap.get(countryK[0]).sort(Integer::compareTo);
                sorted.add(countryK[0]);
            }
            out.println(countryYearsMap.get(countryK[0]).get(Integer.parseInt(countryK[1]) - 1));
        }

        out.close();
    }
}
