package solvers;


import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        ArrayList<HashMap<Integer, Integer>> adjList = Parser.parse(adjMatrix);
        int numVertices = adjList.size();

// Initialize D and P arrays
        int[] D = new int[numVertices];
        int[] P = new int[numVertices];
        Arrays.fill(D, Integer.MAX_VALUE);
        Arrays.fill(P, -1);
        D[source] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            // Relax all edges
            for (int u = 0; u < numVertices; u++) {
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

// Step 3: Finding negative-cost cycles (same for all implementations)
        List<Integer> cycle = (LinkedList<Integer>) GraphUtil.getCycle(P);
        return cycle;

    }

}