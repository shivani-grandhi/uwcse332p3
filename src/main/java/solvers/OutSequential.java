package solvers;


import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.*;

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

        for (int i = 0; i < numVertices; i++) {
            // Create a new array for storing the updated values of D
            int[] newD = new int[numVertices];
            System.arraycopy(D, 0, newD, 0, numVertices); // Manually copy D to newD

            // Relax all edges
            for (int u = 0; u < numVertices; u++) {
                HashMap<Integer, Integer> edges = adjList.get(u);
                for (int v : edges.keySet()) {
                    int weight = edges.get(v);
                    if (newD[v] > D[u] + weight) {
                        newD[v] = D[u] + weight;
                        P[v] = u;
                    }
                }
            }

            // Update D with the new values
            System.arraycopy(newD, 0, D, 0, numVertices);
        }

// Step 3: Finding negative-cost cycles (same for all implementations)
        List<Integer> cycle = (LinkedList<Integer>) GraphUtil.getCycle(P);
        return cycle;

    }

}