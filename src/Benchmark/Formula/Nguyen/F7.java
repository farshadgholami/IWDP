package Benchmark.Formula.Nguyen;

import Symbol.Constant;
import Symbol.GSymbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class F7 extends NF {
    public F7() {
        super();
        vars = 1;
        name = "F7";

        trainingSet = uniformRandom(0, 2, 20);
        testingSet = uniformRandom(0, 2, 20);

        constantSet = new GSymbol[]{new Constant(1)};

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.log(val[0] + 1) + Math.log(val[0] * val[0] + 1);
    }
}

