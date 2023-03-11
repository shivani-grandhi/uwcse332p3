package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ArrayCopyTask extends RecursiveAction {
    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    public static int[] copy(int[] src) {
        int[] dst = new int[src.length];
        pool.invoke(new ArrayCopyTask(src, dst, 0, src.length));
        return dst;
    }

    private final int[] src, dst;
    private final int lo, hi;

    public ArrayCopyTask(int[] src, int[] dst, int lo, int hi) {
        this.src = src;
        this.lo = lo;
        this.hi = hi;
        this.dst = dst;

    }

    @SuppressWarnings("ManualArrayCopy")
    protected void compute() {

        if (hi - lo <= CUTOFF) {
            for(int i = 0; i < hi; i++) {
                dst[i] = src[i];
            }
            return;
        }

        int mid = lo + (hi - lo) / 2;

        ArrayCopyTask left = new ArrayCopyTask(src, dst, lo, mid);
        ArrayCopyTask right = new ArrayCopyTask(src, dst, mid, hi);

        left.fork();
        right.compute();
        left.join();

    }
}