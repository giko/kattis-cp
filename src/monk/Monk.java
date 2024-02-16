package monk;

import java.io.*;

/**
 * Kattis mong problem
 */
public class Monk {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] segmentsParts = in.readLine().split(" ");
        int a = Integer.parseInt(segmentsParts[0]);
        int d = Integer.parseInt(segmentsParts[1]);

        int ascendTime = 0;
        Travel[] ascends = new Travel[a];
        int totalHeight = 0;
        for (int i = 0; i < a; i++) {
            String[] ascendParts = in.readLine().split(" ");
            int h = Integer.parseInt(ascendParts[0]);
            int t = Integer.parseInt(ascendParts[1]);
            ascendTime += t;
            totalHeight += h;
            ascends[i] = new Travel(h, t);
        }

        int descendTime = 0;
        Travel[] descends = new Travel[d];
        for (int i = 0; i < d; i++) {
            String[] descendParts = in.readLine().split(" ");
            int h = Integer.parseInt(descendParts[0]);
            int t = Integer.parseInt(descendParts[1]);
            descendTime += t;
            descends[i] = new Travel(h, t);
        }

        // bsta search space is 0 to maxTime
        double hi = Math.max(ascendTime, descendTime);
        double lo = 0;

        //need to find minimal time where Heights are equal (can be multiple points)
        while (hi - lo > 1e-7) {
            double mid = (hi + lo) / 2;
            int compare = compareHeights(mid, ascends, descends, totalHeight);
            if (compare == 0) {
                double subtractedTime = getSubtractedTime(mid, ascends, descends);

                out.printf("%.6f\n", mid - subtractedTime);
                break;
            } else if (compare < 0) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        out.close();
    }

    private static double getSubtractedTime(double mid, Travel[] ascends, Travel[] descends) {
        double timeLeft = mid;
        double canSubtractAscendTime = 0;
        for (Travel ascend : ascends) {
            if (timeLeft < ascend.t) {
                if (ascend.h == 0) {
                    canSubtractAscendTime = timeLeft;
                }
                break;
            }
            timeLeft -= ascend.t;
        }
        timeLeft = mid;
        double canSubtractDescendTime = 0;
        for (Travel descend : descends) {
            if (timeLeft < descend.t) {
                if (descend.h == 0) {
                    canSubtractDescendTime = timeLeft;
                }
                break;
            }
            timeLeft -= descend.t;
        }
        return Math.min(canSubtractAscendTime, canSubtractDescendTime);
    }

    public static int compareHeights(double time, Travel[] ascends, Travel[] descends, int totalHeight) {
        double ascendHeight = computeHeight(time, ascends);
        double descendHeight = totalHeight - computeHeight(time, descends);

        if (Math.abs(ascendHeight - descendHeight) < 1e-7) {
            return 0;
        } else if (ascendHeight < descendHeight) {
            return -1;
        } else {
            return 1;
        }
    }

    public static double computeHeight(double time, Travel[] travels) {
        double height = 0;
        double timeLeft = time;
        for (int i = 0; i < travels.length; i++) {
            if (timeLeft < travels[i].t) {
                double speed = (double) travels[i].h / travels[i].t;
                return height + speed * timeLeft;
            }
            height += travels[i].h;
            timeLeft -= travels[i].t;
        }
        return height;
    }
}

class Travel {
    int h;
    int t;

    public Travel(int h, int t) {
        this.h = h;
        this.t = t;
    }
}