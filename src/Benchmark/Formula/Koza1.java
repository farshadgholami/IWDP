package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class Koza1 extends Formula {
    public Koza1() {
        vars = 1;
        name = "Koza1";

        trainingSet = linearlySpaced(-1, 1, 20);
        testingSet = linearlySpaced(-1, 1, 20);

        nonTerminalSet = new GSymbol[]{
                new Plus(),
                new Minus(),
                new Multi(),
                new DivisionProtected(),
                new Sin(),
                new Cos(),
                new Exponential(),
                new LnAbs()
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        double a = val[0];
        double b = Math.pow(a, 4) + Math.pow(a, 3) + Math.pow(a, 2) + a;

        return b;
    }
}
