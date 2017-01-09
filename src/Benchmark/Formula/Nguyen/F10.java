package Benchmark.Formula.Nguyen;

/**
 * Created by farsh on 11/20/2016.
 */
public class F10 extends F9 {

    public F10() {
        super();
        name = "F10";
    }

    public double objectiveFunction(double[] val) {
        return 2 * Math.sin(val[0]) * Math.cos(val[1]);
    }
}
