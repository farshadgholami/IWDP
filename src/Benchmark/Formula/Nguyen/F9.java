package Benchmark.Formula.Nguyen;

/**
 * Created by farsh on 11/20/2016.
 */
public class F9 extends NF {
    public F9() {
        super();
        vars = 2;
        name = "F9";

        trainingSet = uniformRandom(-1, 1, 100);
        testingSet = uniformRandom(-1, 1, 100);

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.sin(val[0]) + Math.sin(Math.pow(val[1], 2));
    }
}


