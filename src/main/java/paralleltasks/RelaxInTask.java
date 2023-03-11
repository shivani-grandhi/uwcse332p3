package paralleltasks;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private int[] distancesFromSource;
    private int[] distancesToVertex;
    private int[] p;
    private int startIdx, hi;
    private ArrayList<HashMap<Integer, Integer>> adjList;

    public RelaxInTask(int[] distancesFromSource, int[] distancesToVertex, int[] p, int startIdx, int hi, ArrayList<HashMap<Integer, Integer>> adjList) {

        this.distancesFromSource = distancesFromSource;
        this.distancesToVertex = distancesToVertex;
        this.startIdx = startIdx;
        this.hi = hi;
        this.adjList = adjList;
        this.p = p;
    }

    protected void compute() {
        if(hi - startIdx <= CUTOFF) {
            sequential(distancesFromSource, distancesToVertex,  p, startIdx, adjList);
            return;
        }

        int mid = startIdx + (hi - startIdx)/2;
        RelaxInTask left = new RelaxInTask(distancesFromSource, distancesToVertex,  p, startIdx, mid, adjList);
        RelaxInTask right = new RelaxInTask(distancesFromSource, distancesToVertex, p, mid, hi, adjList);
        left.fork();
        right.compute();
        left.join();

    }

    public static void sequential(int[] distancesFromSource, int[] distancesToVertex, int[] p, int startIdx, ArrayList<HashMap<Integer, Integer>> adjList) {

        int weight = 0;

        for (int h = 0; h < adjList.size(); h++) {


            if (adjList.get(startIdx).containsKey(h) && adjList.get(h) != null && distancesFromSource[h] != Integer.MAX_VALUE) {
                weight = adjList.get(startIdx).get(h);
                if (distancesToVertex[startIdx] > distancesFromSource[h] - weight) {
                    distancesToVertex[startIdx] = distancesFromSource[h] - weight;
                    p[startIdx] = h;
                }
            }
        }
    }

    public static void parallel(int[] distancesFromSource, int[] distancesToVertex, int[] p, ArrayList<HashMap<Integer, Integer>> adjList) {
        pool.invoke(new RelaxInTask(distancesFromSource, distancesToVertex, p, 0, distancesFromSource.length, adjList));
    }

}