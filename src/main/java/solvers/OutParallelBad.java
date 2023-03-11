package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static paralleltasks.RelaxOutTaskBad.parallel;

public class OutParallelBad implements BellmanFordSolver {
    public List<Integer> solve(int[][] adjMatrix, int source) {
        int l = adjMatrix.length;
        ArrayList<HashMap<Integer, Integer>> adjList = Parser.parse(adjMatrix);
        int len = adjList.size();
        int[] D1 = new int[l];
        int[] D2 = new int[l];
        int[] P = new int[l];

        for (int i = 0; i < len; i++) {
            D1[i] = Integer.MAX_VALUE;
            P[i] = -1;
        }
        D1[source] = 0;

        for (int i = 0; i < l; i++) {
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
