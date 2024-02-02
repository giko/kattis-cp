package leet;

class SolutionFenwick {
    public int kBigIndices(int[] nums, int k) {
        int l = nums.length;
        if (l < 2 * k + 1) {
            return 0;
        }
        FenwickTree left = new FenwickTree(l + 1);
        FenwickTree right = new FenwickTree(l + 1);
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                left.update(nums[i], 1);
            } else {
                right.update(nums[i], 1);
            }
        }
        int ans = 0;
        for (int i = k; i < nums.length - k; i++) {
            int num = nums[i];
            right.update(num, -1);
            if (left.rsq(num - 1) >= k && right.rsq(num - 1) >= k) {
                ++ans;
                continue;
            }
            left.update(num, 1);
        }
        return ans;
    }
}

class FenwickTree {
    private int[] tree;

    public FenwickTree(int size) {
        tree = new int[size + 1];
    }

    public void update(int i, int val) {
        for (++i; i < tree.length; i += i & -i) tree[i] += val;
    }

    public long rsq(int i) {
        long sum = 0;
        for (++i; i > 0; i -= i & -i) sum += tree[i];
        return sum;
    }

    public long rsq(int i, int j) {
        return rsq(j) - rsq(i - 1);
    }
}


