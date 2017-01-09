package Benchmark.Formula.Nguyen;

import Symbol.Constant;
import Symbol.GSymbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class F8 extends NF {
    public F8() {
        super();
        vars = 1;
        name = "F8";

        trainingSet = uniformRandom(0, 4, 20);
        testingSet = uniformRandom(0, 4, 20);

        constantSet = new GSymbol[]{new Constant(1)};

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.sqrt(val[0]);
    }
}


