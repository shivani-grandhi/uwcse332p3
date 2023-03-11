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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class OutParallelLock implements BellmanFordSolver {

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

        Lock[][] lock = new Lock[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                lock[i][j] = new ReentrantLock();
            }
        }

        for (int i = 0; i < col; i++) {
            D2 = ArrayCopyTask.copy(D1);
            RelaxOutTaskLock.parallel(D2, D1, P, adjList);

        }

        LinkedList<Integer> cycle = (LinkedList<Integer>) GraphUtil.getCycle(P);

        if (cycle.size() <= 1) {
            return new ArrayList<Integer>();
        }

        return cycle;

    }
}