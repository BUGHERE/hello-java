package Algorightm;

import java.util.*;

class Dinic_ {
    static final int INF = Integer.MAX_VALUE;
    List<List<Edge>> graph;
    int[] level;  // 层次图
    int[] iter;  // 弧优化，确保在DFS遍历过程中不重复遍历同一条边
    static class Edge {
        int to, cap, rev;
        Edge(int to, int cap, int rev) {
            this.to = to;
            this.cap = cap;
            this.rev = rev;
        }
    }
    Dinic_(int V) {
        graph = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }
    }

    // rev->记录反向路径
    void addEdge(int from, int to, int cap) {
        graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
        graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
    }

    boolean buildLevelGraph(int source, int sink) {
        level = new int[graph.size()];
        Arrays.fill(level, -1);
        level[source] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty()) {  // 层序遍历
            int v = queue.poll();
            for (Edge e : graph.get(v)) {
                if (e.cap > 0 && level[e.to] == -1) {
                    level[e.to] = level[v] + 1;
                    queue.add(e.to);
                }
            }
        }
        return level[sink] != -1;
    }

    int dfs(int v, int sink, int minFlow) {
        if (v == sink) {
            return minFlow;
        }
        for (int i = iter[v]; i < graph.get(v).size(); i++) {
            Edge e = graph.get(v).get(i);
            if (e.cap > 0 && level[e.to] == level[v] + 1) {
                int flow = dfs(e.to, sink, Math.min(minFlow, e.cap));
                if (flow > 0) {
                    e.cap -= flow;
                    graph.get(e.to).get(e.rev).cap += flow;
                    return flow;
                }
            }
            iter[v]++;
        }
        return 0;
    }

    int maxFlow(int source, int sink) {
        int maxFlow = 0;
            iter = new int[graph.size()];
        while (buildLevelGraph(source, sink)) {
            int flow;
            while ((flow = dfs(source, sink, INF)) > 0) {
                maxFlow += flow;
            }
        }
        return maxFlow;
    }

    public static void main(String[] args) {
        Dinic_ dinic = new Dinic_(6);
        dinic.addEdge(0, 1, 10);
        dinic.addEdge(0, 2, 10);
        dinic.addEdge(1, 2, 2);
        dinic.addEdge(1, 3, 4);
        dinic.addEdge(1, 4, 8);
        dinic.addEdge(2, 4, 9);
        dinic.addEdge(3, 5, 10);
        dinic.addEdge(4, 3, 6);
        dinic.addEdge(4, 5, 10);
        int res = dinic.maxFlow(0, 5);
        System.out.println("res = " + res);
    }
}