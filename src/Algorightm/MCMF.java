package Algorightm;

import java.util.*;

/**
 * EK + SPFA
 */
public class MCMF {
    private static final int INF = Integer.MAX_VALUE;
    int costs[];  // 额外参数costs（费用）
    int dists[], counts[];  // SPFA

    // 因为没有dfs自动记录，所以需要last存储上一个节点（存储路径），flow记录当前节点的最大流量
    int last[], flow[];
    // n->E, m->V, s->start, t->end, cur->弧优化, [heads, nexts, tos, weights]->链式向前星
    int n, m, s, t, lv[], cur[], heads[], nexts[], tos[], weights[];

    public MCMF(int n, int s, int t, int edges[][]) {
        this.n = n;
        this.s = s;
        this.t = t;
        m = edges.length;
        int edgeNum = 1;  // range: [2, 2*m+1]
        costs = new int[n + 1];
        dists = new int[n + 1];
        counts = new int[n+1];
        last = new int[n + 1];
        flow = new int[n + 1];
        lv = new int[n + 1];  // [1, n] 或 [0, n-1]
        heads = new int[n + 1];
        cur = new int[n + 1];
        tos = new int[2 * m + 2];
        nexts = new int[2 * m + 2];
        weights = new int[2 * m + 2];
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
        Arrays.fill(dists, 127);
        Deque<Integer> q = new LinkedList<>();
        q.offer(s);
        flow[s] = INF;
        dists[s] = 0;
        while (!q.isEmpty()) {
            int p = q.poll();
            for (int eg = heads[p]; eg != 0; eg = nexts[eg]) {
                int to = tos[eg], vol = weights[eg];
                if (vol > 0 && dists[to] > dists[p] + costs[eg]) {  // 如果残余容量大于0且未访问过
                    last[to] = eg;
                    flow[to] = Math.min(flow[p], vol);
                    dists[to] = dists[p] + costs[eg];
                    q.offer(to);
                    if (!q.contains(to)) {  // 入队的前提是此时v不在q中，否则程序虽正确，但复杂度将不再是O(|V||E|)
                        q.add(to);
                        if (counts[to] > n - 1) {  // 负圈检测
                            System.out.println("Negative Cycle Found!");
                            return false;
                        } else {
                            counts[to] += 1;
                        }
                    }
                }
            }
        }
        return last[t] != -1;
    }

    public long mcmf() {
        long res = 0, minCost = 0;
        while (bfs()) {
            res += flow[t];
            minCost += dists[t] * flow[t];
            for (int cur = t; cur != s; cur = tos[last[cur] ^ 1]) {  // 从汇点原路返回更新残余容量
                weights[last[cur]] -= flow[t];
                weights[last[cur] ^ 1] += flow[t];
            }
        }
        return res;
    }
}