package paralleltasks;

import cse332.exceptions.NotYetImplementedException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    final int lo, hi;

    public RelaxInTask() {
        throw new NotYetImplementedException();
    }

    protected void compute() {
        throw new NotYetImplementedException();
    }

    public static void sequential() {
        throw new NotYetImplementedException();
    }

    public static void parallel() {
        throw new NotYetImplementedException();
    }

}
