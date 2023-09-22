package Algorightm;

import java.util.*;
public class Dinic {
    // n->E, m->V, s->start, t->end, cur->弧优化, [heads, nexts, tos, weights]->链式向前星
    int n, m, s, t, lv[], cur[], heads[], nexts[], tos[], weights[];

    public Dinic(int n, int s, int t, int edges[][]) {
        this.n = n; this.s = s; this.t = t;
        lv = new int[n+1];  // [1, n] 或 [0, n-1]
        heads = new int[n+1];
        cur = new int[n+1];
        m = edges.length;
        int edgeNum = 1;  // range: [2, 2*m+1]
        tos = new int[2*m + 2];
        nexts = new int[2*m + 2];
        weights = new int[2*m + 2];
        for (int[] edge : edges) {
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
        Arrays.fill(lv, -1);
        lv[s] = 0;
        System.arraycopy(heads, 0, cur, 0, n);
        Deque<Integer> q = new LinkedList<>();
        q.offer(s);
        while (!q.isEmpty()) {
            int p = q.poll();
            for (int eg = heads[p]; eg != 0; eg = nexts[eg]) {
                int to = tos[eg], vol = weights[eg];
                if (vol > 0 && lv[to] == -1) {
                    lv[to] = lv[p] + 1;
                    q.offer(to);
                }
            }
        }
        return lv[t] != -1;
    }
    public int dfs(int p, int flow) {
        if (p == t) return flow;
        int rmn = flow;  // 剩余的流量
        for (int eg = heads[p]; eg != 0 && rmn != 0; eg = nexts[eg]) {
//            cur[p] = eg;  // 弧优化，这次dfs不再选择已经走过的边（不一定优化，可能更慢）
            int to = tos[eg], vol = weights[eg];
            if (vol > 0 && lv[to] == lv[p] + 1) {  // 往层数高的方向增广
                int c = dfs(to, Math.min(vol, rmn));  // 尽可能多地传递流量
                rmn -= c;
                weights[eg] -= c;  // 更新残余容量
                weights[eg^1] += c;  // 链式前向星的cnt需要初始化为1（或-1）才能这样求反向边
            }
        }
        return flow - rmn;
    }
    public long dinic() {
        long res = 0;
        while (bfs()) {
            res += dfs(s, Integer.MAX_VALUE);
        }
        return res;
    }
}
