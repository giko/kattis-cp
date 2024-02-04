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
                int sum = 0;
                int doublesi = rehersals[i] & rehersals[j];
                for (int k = 0; k <= maxChar; k++) {
                    if ((doublesi & (1 << k)) != 0) {
                        ++sum;
                    }
                }
                doubles[i][j] = sum;
            }
        }

        int[] indices = new int[R];
        for (int i = 0; i < R; i++) {
            indices[i] = i;
        }

        int permutations = factorial(R);
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < permutations; i++) {
            int sum = 0;
            for (int j = 1; j < R; j++) {
                int minIndex = Math.min(indices[j - 1], indices[j]);
                int maxIndex = Math.max(indices[j - 1], indices[j]);
                sum += doubles[minIndex][maxIndex];
            }
            result = Math.min(result, sum);
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
        // find the first pair of two successive numbers nums[index] and nums[index + 1]
        // from the right, which satisfy nums[index] < [index + 1]
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }

        // swap the number nums[index] with the number a[larger] which is just larger than itself
        if (index >= 0) {
            int larger = nums.length - 1;
            while (larger >= 0 && nums[larger] <= nums[index]) {
                larger--;
            }
            swap(nums, index, larger);
        }

        // reverse the numbers following a[index] to get the next smallest lexicographic permutation
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
