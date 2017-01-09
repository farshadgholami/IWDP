package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class F8 extends Formula {
    public F8() {
        vars = 1;
        name = "F8";

        trainingSet = evenlySpaced(1, 100, 1);
        testingSet = evenlySpaced(1, 100, 0.1);

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
        return Math.sqrt(val[0]);
    }
}
