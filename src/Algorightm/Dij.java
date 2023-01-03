package Algorightm;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Dij算法适用于最短路径问题
 *
 * 0 1 0  0->0, 1->1路径长度（花费）为1；0->1, 1->0路径为2
 * 1 0 1  可以向下走、向左、向右走、求从左上到右下的最短路径
 * 1 1 1
 */
class DijSolution {
    public static int getShortestPathLength(int[][] path) {
        /**
         * 其实可以排除向左走的情况，因为路径长度只有1和2两种可能，所以向左走肯定会导致总路径长度增大
         * 但是为了考虑通用性，这里用dijkstra
         */
        int n = path.length;
        if (n == 0) return 0;
        int m = path[0].length;
        if (m == 0) return 0;
        // build graph
        int[] graph = new int[m*n];
        // l[2] = <1 ,3> means "the distance between point2 and point1 is 3
        List<Pair<Integer, Integer>>[] l = new ArrayList[m*n];
        for (int i = 0; i < m*n; ++i) {
            l[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int index = i * m + j;
                if (j != 0) {  // to left
                    l[index].add(new Pair<>(index-1, path[i][j] == path[i][j-1] ? 1 : 2));
                }
                if (j != m-1) {  // to right
                    l[index].add(new Pair<>(index+1, path[i][j] == path[i][j+1] ? 1 : 2));
                }
                if (i != n-1) {  // down
                    l[index].add(new Pair<>(index+m, path[i][j] == path[i+1][j] ? 1 : 2));
                }
            }
        }

        int result = dij(0, n, m, l);  // u = 0?
        System.out.println(result);
        return result;
    }

    private static int dij(int u, int n, int m, List<Pair<Integer, Integer>>[] l) {
        int[] dis = new int[n*m];  // dis[i] means "the shortest path length between i and u"
        boolean[] vis = new boolean[n*m];  // vis[i] means "whether point i is visited"
        for (int i = 0; i < n*m; ++i) {
            dis[i] = Integer.MAX_VALUE;
            vis[i] = false;
        }
        dis[u] = 0;
        // Pair<1, 2> 表示到点2的最短路径为1.  // PriorityQueue默认小的在堆顶，正好符合最小路径
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b)->a.getKey() - b.getKey());
        pq.offer(new Pair<>(dis[u], u));
        while (!pq.isEmpty()) {
            // pair key用于排序，pair value表示对应的点
            Pair<Integer, Integer> poll = pq.poll();
            int point = poll.getValue();  // 最短路径的点
            if (vis[point]) {
                continue;
            }
            vis[point] = true;
            for (Pair<Integer, Integer> pair: l[point]) {
                if (dis[point] + pair.getValue() < dis[pair.getKey()]) {  // 如果从当前点的最短路径距离与到下一个点的距离之和小于下一个点的最短路径距离，
                    dis[pair.getKey()] = dis[point] + pair.getValue();    // 则把下一个点加入优先队列（Dijkstra核心）
                    pq.offer(new Pair<>(dis[pair.getKey()], pair.getKey()));
                }
            }
        }
        return dis[m*n-1];
    }
}


public class Dij {
    public static void main(String[] args) {
        int[][] path = {{1, 0, 0}, {1, 1, 1}, {0, 0, 1}};
        path = new int[][]{{1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 0, 0}};
        DijSolution.getShortestPathLength(path);
    }
}
