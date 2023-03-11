package paralleltasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxOutTaskBad extends RecursiveAction {
    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private int[] src;
    private int[] dst;
    private int[] p;
    private int lo, hi;
    private ArrayList<HashMap<Integer, Integer>> adjList;

    public RelaxOutTaskBad(int[] src, int[] dst, int[] p, int lo, int hi, ArrayList<HashMap<Integer, Integer>> adjList) {

        this.src = src;
        this.dst = dst;
        this.lo = lo;
        this.hi = hi;
        this.adjList = adjList;
        this.p = p;
    }

    protected void compute() {
        if(hi - lo <= CUTOFF) {
            sequential(src, dst,  p, lo, adjList);
            return;

            //         return new int[0];
        }

        int mid = lo + (hi - lo)/2;
        RelaxOutTaskBad left = new RelaxOutTaskBad(src, dst,  p, lo, mid, adjList);
        RelaxOutTaskBad right = new RelaxOutTaskBad(src, dst, p, mid, hi, adjList);
        left.fork();
        right.compute();
        left.join();

        return;
    }
    public static void sequential(int[] src, int[] dst, int[] p, int lo, ArrayList<HashMap<Integer, Integer>> adjList) {
        int weight;
        for (int h = 0; h < adjList.size(); h++) {
            if (adjList.get(lo).containsKey(h) && adjList.get(lo) != null && src[lo] != Integer.MAX_VALUE) {
                weight = adjList.get(lo).get(h);
                if (dst[h] > src[lo] + weight) {
                    dst[h] = src[lo] + weight;
                    p[h] = lo;
                }
            }
        }
    }

    public static void parallel(int[] src, int[] dst, int[] p, ArrayList<HashMap<Integer, Integer>> adjList) {
        pool.invoke(new RelaxOutTaskBad(src, dst, p, 0, src.length, adjList));


    }
}