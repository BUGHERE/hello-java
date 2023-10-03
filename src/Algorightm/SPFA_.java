package Algorightm;

import java.util.*;



/**
 * 邻接表
 */
class SPFA_ {
    final int INF = Integer.MAX_VALUE;
    // n->num, s->start, dists->distances, counts->记录顶点入队次数，用于负圈检测
    int n, s, dists[], counts[];
    List<int[]> g[];

    public SPFA_(int n, int s, List<int[]> g[]) {
        this.n = n;
        this.s = s;
        this.g = g;
        this.dists = new int[n];
        this.counts = new int[n];
    }

    public boolean bfs() {
        Arrays.fill(dists, INF);
        dists[s - 1] = 0;
        Deque<Integer> q = new LinkedList<>();
        q.add(s - 1);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int vw[] : g[u]) {
                int v = vw[0], w = vw[1];
                if (dists[u] + w < dists[v]) {  // 松弛u的邻接顶点
                    dists[v] = dists[u] + w;
                    if (!q.contains(v)) {  // 入队的前提是此时v不在q中，否则程序虽正确，但复杂度将不再是O(|V||E|)
                        q.add(v);
                        if (counts[v] > n - 1) {  // 负圈检测
                            System.out.println("Negative Cycle Found!");
                            return false;
                        } else {
                            counts[v] += 1;
                        }
                    }
                }
            }
        }
        return true;
    }
}

class SolutionSPFA_ {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<int[]> g[] = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : times) {
            int u = e[0] - 1, v = e[1] - 1, w = e[2];
            g[u].add(new int[]{v, w});
        }
        int res = 0;
        SPFA_ spfa = new SPFA_(n, k, g);
        spfa.bfs();
        for (int dist : spfa.dists) { // 找最短路长度最大者
            if (dist == spfa.INF) return -1; // 存在距离未更新的顶点
            if (dist > res) res = dist;
        }
        return res;
    }
}