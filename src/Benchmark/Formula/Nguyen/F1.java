package Benchmark.Formula.Nguyen;

import Symbol.Constant;
import Symbol.GSymbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class F1 extends NF {
    public F1() {
        super();
        vars = 1;
        name = "F1";

        trainingSet = uniformRandom(-1, 1, 20);
        testingSet = uniformRandom(-1, 1, 20);

        constantSet = new GSymbol[]{new Constant(1)};

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.pow(val[0], 3) + Math.pow(val[0], 2) + Math.pow(val[0], 1);
    }
}
