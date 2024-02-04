package dancerecital;

import java.io.*;

public class DanceRecital {
    public static void main(String[] a) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int R = Integer.parseInt(in.readLine());
        int[] rehersals = new int[R];
        int maxChar = 0;
        for (int i = 0; i < R; i++) {
            for (char c : in.readLine().toCharArray()) {
                rehersals[i] |= 1 << (c - 'A');
                maxChar = Math.max(maxChar, c - 'A');
            }
        }

        int[][] doubles = new int[R][R];
        for (int i = 0; i < R; i++) {
            for (int j = i + 1; j < R; j++) {
                doubles[i][j] = Integer.bitCount(rehersals[i] & rehersals[j]);
            }
        }

        int[] indices = new int[R];
        for (int i = 0; i < R; i++) {
            indices[i] = i;
        }

        int permutations = factorial(R);
        int result = Integer.MAX_VALUE;
        outer:
        for (int i = 0; i < permutations; i++) {
            int sum = 0;
            for (int j = 1; j < R; j++) {
                int minIndex = Math.min(indices[j - 1], indices[j]);
                int maxIndex = Math.max(indices[j - 1], indices[j]);
                sum += doubles[minIndex][maxIndex];
                if (sum >= result) {
                    nextPermutation(indices);
                    continue outer;
                }
            }
            result = sum;
            nextPermutation(indices);
        }
        out.println(result);
        out.close();
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void nextPermutation(int[] nums) {
        int index = nums.length - 2;
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }
        if (index >= 0) {
            int larger = nums.length - 1;
            while (larger >= 0 && nums[larger] <= nums[index]) {
                larger--;
            }
            swap(nums, index, larger);
        }
        reverse(nums, index + 1);
    }

    private static void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
