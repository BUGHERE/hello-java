package Algorightm;

/**
 * 线段树（静态）：数组实现
 * 区域和检索 - 数组可修改
 * https://leetcode.cn/problems/range-sum-query-mutable/
 */
class SegmentTree {
    int[] nums;
    int len;
    int[] tree;
    void build(int idx, int l, int r) {
        if (l == r) {
            tree[idx] = nums[l];
        }
        else {
            int m = l + (r - l) / 2;
            if (l <= m) build(2*idx, l, m);
            if (m + 1 <= r) build(2*idx+1, m+1, r);
            tree[idx] = tree[2*idx] + tree[2*idx+1];
        }
    }
    int query(int idx, int l, int r, int tl, int tr) {
        if (tl <= l && r <= tr) {
            return tree[idx];
        }
        else {
            int m = l + (r - l) / 2, ans = 0;
            if (tl <= m) ans += query(2*idx, l, m, tl, tr);
            if (m + 1 <= tr) ans += query(2*idx+1, m+1, r, tl, tr);
            return ans;
        }
    }
    void upd(int idx, int l, int r, int tl, int tr, int val) {
        if (tl <= l && r <= tr) {
            tree[idx] = val;
        }
        else {
            int m = l + (r - l) / 2;
            if (tl <= m) upd(2*idx, l, m, tl, tr, val);
            if (m + 1 <= tr) upd(2*idx+1, m+1, r, tl, tr, val);
            tree[idx] = tree[2*idx] + tree[2*idx+1];
        }
    }
    public SegmentTree(int[] nums) {
        this.nums = nums;
        this.len = nums.length;
        tree = new int[4*len];
        build(1, 0, len-1);
    }

    public void update(int index, int val) {
        upd(1, 0, len-1, index, index, val);
    }

    public int sumRange(int left, int right) {
        return query(1, 0, len-1, left, right);
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */