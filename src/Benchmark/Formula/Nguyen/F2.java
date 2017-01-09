package Benchmark.Formula.Nguyen;

/**
 * Created by farsh on 11/20/2016.
 */
public class F2 extends F1 {
    public F2() {
        super();
        name = "F2";
    }

    public double objectiveFunction(double[] val) {
        return Math.pow(val[0], 4) + Math.pow(val[0], 3) + Math.pow(val[0], 2) + Math.pow(val[0], 1);
    }
}