package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.List;

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        Object g = Parser.parseInverse(adjMatrix);

        throw new NotYetImplementedException();
    }

}