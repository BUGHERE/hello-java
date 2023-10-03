package Algorightm;

import java.util.*;


/**
 * 链式向前星
 */
class SPFA {
    final int INF = Integer.MAX_VALUE;
    // dists->distances, counts->记录顶点入队次数，用于负圈检测, inQueue->是否在当前队列
    int dists[], counts[], inQueue[];
    // n->E, m->V, s->start, [heads, nexts, tos, weights]->链式向前星
    int n, m, s, heads[], nexts[], tos[], weights[];

    public SPFA(int n, int s, int edges[][]) {
        this.n = n;
        this.s = s;
        m = edges.length;
        int edgeNum = 1;  // range: [2, 2*m+1]
        dists = new int[n+1];
        counts = new int[n+1];
        inQueue = new int[n+1];
        heads = new int[n + 1];
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
            // ++edgeNum;
            // nexts[edgeNum] = heads[v];
            // heads[v] = edgeNum;
            // tos[edgeNum] = u;
            // weights[edgeNum] = 0;
        }
    }

    public boolean bfs() {
        Arrays.fill(dists, INF);
        Deque<Integer> q = new LinkedList<>();
        q.offer(s);
        dists[s] = 0;
        while (!q.isEmpty()) {
            int p = q.poll();
            inQueue[p] = 0;
            for (int eg = heads[p]; eg != 0; eg = nexts[eg]) {
                int to = tos[eg];
                if (dists[to] > dists[p] + weights[eg]) {
                    dists[to] = dists[p] + weights[eg];
                    if (inQueue[to] == 0) {
                        inQueue[to] = 1;
                        q.offer(to);
                        if (counts[to] > n - 1) {  // 负圈检测
                            System.out.println("Negative Cycle Found!");
                            return false;
                        }
                        else {
                            counts[to] += 1;
                        }
                    }
                }
            }
        }
        return true;
    }
}

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        int res = 0;
        SPFA spfa = new SPFA(n, k, times);
        spfa.bfs();
        for (int i = 1; i <= n; i++) {  // 找最短路长度最大者
            int dist = spfa.dists[i];
            if (dist == spfa.INF) return -1; // 存在距离未更新的顶点
            if (dist > res) res = dist;
        }
        return res;
    }
}