package Benchmark.Formula.Nguyen;

/**
 * Created by farsh on 11/20/2016.
 */
public class F4 extends F1 {
    public F4() {
        super();
        name = "F4";
    }

    public double objectiveFunction(double[] val) {
        return Math.pow(val[0], 6) + Math.pow(val[0], 5) + Math.pow(val[0], 4) + Math.pow(val[0], 3) + Math.pow(val[0], 2) + Math.pow(val[0], 1);
    }
}