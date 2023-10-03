package Algorightm;

import java.util.*;

public class EK {
    private static final int INF = Integer.MAX_VALUE;
    // 因为没有dfs自动记录，所以需要last存储上一个节点（存储路径），flow记录当前节点的最大流量
    int last[], flow[];
    // n->E, m->V, s->start, t->end, cur->弧优化, [heads, nexts, tos, weights]->链式向前星
    int n, m, s, t, lv[], cur[], heads[], nexts[], tos[], weights[];
    public EK(int n, int s, int t, int edges[][]) {
        this.n = n; this.s = s; this.t = t;
        m = edges.length;
        int edgeNum = 1;  // range: [2, 2*m+1]
        last = new int[n+1];
        flow = new int[n+1];
        lv = new int[n+1];  // [1, n] 或 [0, n-1]
        heads = new int[n+1];
        cur = new int[n+1];
        tos = new int[2*m + 2];
        nexts = new int[2*m + 2];
        weights = new int[2*m + 2];
        for (int[] edge : edges) {  // 链式向前星建图
            int u = edge[0], v = edge[1], w = edge[2];
            // 添加边 (u, v)
            ++edgeNum;
            nexts[edgeNum] = heads[u];
            heads[u] = edgeNum;
            tos[edgeNum] = v;
            weights[edgeNum] = w;
            // 添加反向边 (v, u)
            ++edgeNum;
            nexts[edgeNum] = heads[v];
            heads[v] = edgeNum;
            tos[edgeNum] = u;
            weights[edgeNum] = 0;
        }
    }

    public boolean bfs() {
        Arrays.fill(last, -1);
        Deque<Integer> q = new LinkedList<>();
        q.offer(s);
        flow[s] = INF;
        while (!q.isEmpty()) {
            int p = q.poll();
            if (p == t) {
                break;
            }
            for (int eg = heads[p]; eg != 0; eg = nexts[eg]) {
                int to = tos[eg], vol = weights[eg];
                if (vol > 0 && last[to] == -1) {  // 如果残余容量大于0且未访问过
                    last[to] = eg;
                    flow[to] = Math.min(flow[p], vol);
                    q.offer(to);
                }
            }
        }
        return last[t] != -1;
    }

    public long ek() {
        long res = 0;
        while (bfs()) {
            res += flow[t];
            for (int cur = t; cur != s; cur = tos[last[cur]^1]) {  // 从汇点原路返回更新残余容量
                weights[last[cur]] -= flow[t];
                weights[last[cur]^1] += flow[t];
            }
        }
        return res;
    }
}
