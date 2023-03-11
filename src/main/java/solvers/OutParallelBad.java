package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static paralleltasks.RelaxOutTaskBad.parallel;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        ArrayList<HashMap<Integer, Integer>> adjList = Parser.parse(adjMatrix);
        int len = adjList.size();
        int col = adjMatrix.length;
        int[] D1 = new int[col];
        int[] D2 = new int[col];
        int[] P = new int[col];

        for (int i = 0; i < len; i++) {
            D1[i] = Integer.MAX_VALUE;
            P[i] = -1;
        }
        D1[source] = 0;

        for (int i = 0; i < col; i++) {
            D2 = ArrayCopyTask.copy(D1);
            parallel(D2, D1, P, adjList);

        }

        LinkedList<Integer> cycle = (LinkedList<Integer>) GraphUtil.getCycle(P);

        if (cycle.size() <= 1) {
            return new ArrayList<Integer>();
        }

        return cycle;

    }
}
