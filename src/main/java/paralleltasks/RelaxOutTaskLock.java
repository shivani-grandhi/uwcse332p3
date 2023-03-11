package paralleltasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RelaxOutTaskLock extends RecursiveAction {
    ReentrantLock re;
    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private int[] src;
    private int[] dst;
    private int[] p;
    private int lo, hi;
    ReentrantLock[][] lock;
    private ArrayList<HashMap<Integer, Integer>> adjList;
    public RelaxOutTaskLock(int[] src, int[] dst, int[] p, ArrayList<HashMap<Integer, Integer>> adjList, int lo, int hi, ReentrantLock[][] lock) {
        this.src = src;
        this.dst = dst;
        this.lo = lo;
        this.hi = hi;
        this.adjList = adjList;
        this.p = p;
        this.lock = lock;
    }

    protected void compute() {
        if(hi - lo <= CUTOFF) {
            sequential(src, dst,  p, lo, adjList, lock);
            return;
        }

        int mid = lo + (hi - lo)/2;
        RelaxOutTaskLock left = new RelaxOutTaskLock(src, dst,  p, adjList, lo, mid, lock);
        RelaxOutTaskLock right = new RelaxOutTaskLock(src, dst, p, adjList, mid, hi, lock);
        left.fork();
        right.compute();
        left.join();
    }

    public static void sequential(int[] src, int[] dst, int[] p, int lo, ArrayList<HashMap<Integer, Integer>> adjList, Lock[][] lock) {
        int weight = 0;

        for (int h = 0; h < adjList.size(); h++) {

            lock[lo][h].lock();
            if (adjList.get(lo).containsKey(h) && adjList.get(lo) != null && src[lo] != Integer.MAX_VALUE) {
                weight = adjList.get(lo).get(h);
                if (dst[h] > src[lo] + weight) {
                    dst[h] = src[lo] + weight;
                    p[h] = lo;
                }
            }
            lock[lo][h].unlock();
        }

    }public static void parallel(int[] src, int[] dst, int[] p, ArrayList<HashMap<Integer, Integer>> adjList) {
        ReentrantLock[][] lock = new ReentrantLock[src.length][src.length];
        for(int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                lock[i][j] = new ReentrantLock();
            }
        }
        pool.invoke(new RelaxOutTaskLock(src, dst, p, adjList, 0, src.length, lock));
    }


}