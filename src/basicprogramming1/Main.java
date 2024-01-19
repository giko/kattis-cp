package basicprogramming1;

import java.util.*;

import static java.lang.System.out;

class BasicProgramming1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int t = s.nextInt();
        switch (t) {
            case 1:
                out.println("7");
                break;
            case 2: {
                long a0 = s.nextLong();
                long a1 = s.nextLong();
                if (a0 > a1) {
                    out.println("Bigger");
                } else if (a0 == a1) {
                    out.println("Equal");
                } else {
                    out.println("Smaller");
                }
                break;
            }
            case 3: {
                long[] nums = {s.nextLong(), s.nextLong(), s.nextLong()};
                Arrays.sort(nums);
                out.println(nums[1]);
            }
            break;
            case 4: {
                long sum = 0;
                for (int i = 0; i < n; ++i) {
                    sum += s.nextLong();
                }
                out.println(sum);
            }
            break;
            case 5: {
                long sum = 0;
                for (int i = 0; i < n; ++i) {
                    long ni = s.nextLong();
                    if (ni % 2 == 0) {
                        sum += ni;
                    }
                }
                out.println(sum);
            }
            break;
            case 6: {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; ++i) {
                    sb.append((char) (s.nextLong() % 26 + 'a'));
                }
                out.println(sb);
            }
            break;
            case 7: {
                long nums[] = new long[n];
                for (int i = 0; i < n; ++i) {
                    nums[i] = s.nextLong();
                }

                long i = 0;
                while (true) {
                    if (i >= n) {
                        out.println("Out");
                        break;
                    }
                    if (i == n - 1) {
                        out.println("Done");
                        break;
                    }
                    if (i < 0) {
                        out.println("Cyclic");
                        break;
                    }
                    long oi = i;
                    i = nums[(int) i];
                    nums[(int) oi] = -1;
                }
            }
            break;
        }
    }
}