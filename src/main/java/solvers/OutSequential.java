package solvers;


import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        ArrayList<HashMap<Integer, Integer>> adjList = Parser.parse(adjMatrix);
        int vertices = adjList.size();

        int[] D = new int[vertices];
        int[] P = new int[vertices];
        Arrays.fill(D, Integer.MAX_VALUE);
        Arrays.fill(P, -1);
        D[source] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            // Relax edges
            for (int u = 0; u < vertices; u++) {
                HashMap<Integer, Integer> edges = adjList.get(u);
                for (int v : edges.keySet()) {
                    int weight = edges.get(v);
                    if (D[u] != Integer.MAX_VALUE && D[v] > D[u] + weight) {
                        D[v] = D[u] + weight;
                        P[v] = u;
                    }
                }
            }
        }

        // Step 3
        List<Integer> negativeCycle = new ArrayList<>();
        int[] prev = new int[adjMatrix.length];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = P[i];
        }
        List<Integer> cycle = GraphUtil.getCycle(prev);
        if (cycle.size() > 0) {
            for (int i = 0; i < cycle.size(); i++) {
                negativeCycle.add(cycle.get(i));
            }
        }
        return negativeCycle;
    }
}