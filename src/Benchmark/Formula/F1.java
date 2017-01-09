package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class F1 extends Formula {
    public F1() {
        vars = 1;
        name = "F1";

        trainingSet = evenlySpaced(-1, 1, 0.2);
        testingSet = evenlySpaced(-1, 1, 0.2);

        nonTerminalSet = new GSymbol[]{
                new Plus(),
                new Multi(),
                new OneOnN(),
                new Negative(),
                new Sqrt()
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        return Math.pow(val[0], 3) + Math.pow(val[0], 2) + Math.pow(val[0], 1);
    }
}
