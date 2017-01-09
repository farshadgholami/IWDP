package Benchmark.Formula.Nguyen;

import Symbol.Constant;
import Symbol.GSymbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class F6 extends NF {
    public F6() {
        vars = 1;
        name = "F6";

        trainingSet = uniformRandom(-1, 1, 20);
        testingSet = uniformRandom(-1, 1, 20);

        constantSet = new GSymbol[]{new Constant(1)};

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.sin(val[0]) + Math.sin(val[0] + Math.pow(val[0], 2));
    }
}

