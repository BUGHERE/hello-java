package Algorightm;

/**
 * 适用于区间求和问题
 * 结点数组法动态线段树，线段树每个节点代表一个区间，根节点是最大的区间（这里是[0, 1e9]），当前区间=左孩子区间+右孩子区间
 * 问题描述：实现一个日程表函数：boolean book(int start, int end)
 * 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true
 * 否则，返回 false并且不要将该日程安排添加到日历中
 * https://leetcode.cn/problems/my-calendar-i/
 */
class SegmentTreeDynamicArray {
    /**
     * lIdx、RIdx: 左右孩子下标（因为是动态开点，所以没有满二叉树的性质）
     * val: 节点的值，在这一题表示如果一个日程区间被安排成功，那么这个区间的所有值idx在tr里的值为1，即tr[idx]=1
     * add: 懒标记
     */
    class Node {
        int lIdx, rIdx, val, add;
    }
    // 因为是动态开点，所以需要有个cnt，来计算当前新增的idx
    // 又因为后面使用cnt==0来判断，是否idx被初始化，所以cnt从1开始
    int N = (int)1e9, M = 120010, cnt = 1;  // M就是开的空间大小，空间受限所以M不能太大
    Node[] tr = new Node[M];
    {
        tr[1] = new Node();
    }
    int query(int idx, int curL, int curR, int tgL, int tgR) {
        if (tgL <= curL && curR <= tgR) return tr[idx].val;
        else {
            lazyCreate(idx);
            pushdown(idx, curR-curL+1);
            int curMid = curL + (curR-curL>>1), ans = 0;
            if (tgL <= curMid) ans += query(tr[idx].lIdx, curL, curMid, tgL, tgR);  // 把mid放到left query中考虑
            if (tgR > curMid) ans += query(tr[idx].rIdx, curMid+1, curR, tgL, tgR);
            return ans;
        }
    }
    void lazyCreate(int idx) {  // 动态开点（如果当前节点及其孩子为null，则生成对象）
        if (tr[idx].lIdx == 0) {
            tr[idx].lIdx = ++cnt;
            tr[tr[idx].lIdx] = new Node();
        }
        if (tr[idx].rIdx == 0) {
            tr[idx].rIdx = ++cnt;
            tr[tr[idx].rIdx] = new Node();
        }
    }
    void pushdown(int idx, int len) {  // 下推懒标记给左右孩子，并把当前懒标记清除
        tr[tr[idx].lIdx].add += tr[idx].add;
        tr[tr[idx].rIdx].add += tr[idx].add;
        tr[tr[idx].lIdx].val += (len-len/2)*tr[idx].add;  // len-len/2因为query函数把mid放到left query中考虑
        tr[tr[idx].rIdx].val += (len/2)*tr[idx].add;
        tr[idx].add = 0;
    }
    void update(int idx, int curL, int curR, int tgL, int tgR, int val) {  // 这一题的val就是1
        if (tgL <= curL && curR <= tgR) {
            tr[idx].val += (curR-curL+1)*val;
            tr[idx].add += val;
        }
        else {
            lazyCreate(idx);
            pushdown(idx, curR-curL+1);
            int curMid = curL + (curR-curL>>1);
            if (tgL <= curMid) update(tr[idx].lIdx, curL, curMid, tgL, tgR, val);
            if (tgR > curMid) update(tr[idx].rIdx, curMid+1, curR, tgL, tgR, val);
            pushup(idx);
        }
    }
    void pushup(int idx) {
        tr[idx].val = tr[tr[idx].lIdx].val + tr[tr[idx].rIdx].val;
    }
    public boolean book(int start, int end) {  // 0 <= start < end <= 10^9
        // end-1因为这个问题边界可以重复，[10, 20]和[20, 30]都可以添加
        if (query(1, 0, N-1, start, end-1) > 0) return false;
        update(1, 0, N-1, start, end-1, 1);
        return true;
    }
}

/**
 * 结点指针法动态线段树
 * 
 * Range模块是跟踪数字范围的模块。设计一个数据结构来跟踪表示为 半开区间 的范围并查询它们。
 *
 * 半开区间 [left, right) 表示所有 left <= x < right 的实数 x 。
 *
 * 实现 RangeModule 类:
 *
 * RangeModule() 初始化数据结构的对象。
 * void addRange(int left, int right) 添加 半开区间 [left, right)，跟踪该区间中的每个实数。添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 [left, right) 中尚未跟踪的任何数字到该区间中。
 * boolean queryRange(int left, int right) 只有在当前正在跟踪区间 [left, right) 中的每一个实数时，才返回 true ，否则返回 false 。
 * void removeRange(int left, int right) 停止跟踪 半开区间 [left, right) 中当前正在跟踪的每个实数。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/range-module
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class SegmentTreeDynamicPoint {
    class Node {
        Node l, r;
        int val, lazy;
    }
    Node root = new Node();
    void lazyCreate(Node cur) {
        if (cur.l == null) cur.l = new Node();
        if (cur.r == null) cur.r = new Node();
    }
    void pushDown(Node cur, int len) {
        if (cur.lazy == 0) return;
        else if (cur.lazy == 1) {
            cur.l.val = (len - len/2);
            cur.r.val = len/2;
        }
        else if (cur.lazy == -1) {
            cur.l.val = 0;
            cur.r.val = 0;
        }
        cur.l.lazy = cur.lazy;
        cur.r.lazy = cur.lazy;
        cur.lazy = 0;
    }
    void pushUp(Node cur) {
        cur.val = cur.l.val + cur.r.val;
    }
    void upd(Node cur, int l, int r, int tl, int tr, int val) {
        if (tl <= l && r <= tr) {
            cur.val = val == 1 ? r - l + 1 : 0;
            cur.lazy = val;
        }
        else {
            lazyCreate(cur);
            pushDown(cur, r-l+1);
            int m = l + (r-l)/2;
            if (tl <= m) upd(cur.l, l, m, tl, tr, val);
            if (m+1 <= tr) upd(cur.r, m+1, r, tl, tr, val);
            pushUp(cur);
        }
    }
    int query(Node cur, int l, int r, int tl, int tr) {
        if (tl <= l && r <= tr) {
            return cur.val;
        }
        else {
            lazyCreate(cur);
            pushDown(cur, r-l+1);
            int m = l + (r-l)/2, ans = 0;
            if (tl <= m) ans += query(cur.l, l, m, tl, tr);
            if (m+1 <= tr) ans += query(cur.r, m+1, r, tl, tr);
            pushUp(cur);
            return ans;
        }
    }
    int L = 1, R = (int)1e9;
    public void addRange(int left, int right) {
        upd(root, L, R, left, right-1, 1);
    }

    public boolean queryRange(int left, int right) {
        return query(root, L, R, left, right-1) == right - left;
    }

    public void removeRange(int left, int right) {
        upd(root, L, R, left, right-1, -1);
    }
}