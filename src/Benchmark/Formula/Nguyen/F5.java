package Benchmark.Formula.Nguyen;

import Symbol.Constant;
import Symbol.GSymbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class F5 extends NF {
    public F5() {
        vars = 1;
        name = "F5";

        trainingSet = uniformRandom(-1, 1, 20);
        testingSet = uniformRandom(-1, 1, 20);

        constantSet = new GSymbol[]{new Constant(1)};

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.sin(Math.pow(val[0], 2)) * Math.cos(val[0]) - 1;
    }
}
