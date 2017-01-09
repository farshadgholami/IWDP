package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Pouya Payandeh on 2/4/2016.
 */
public class Pagie1 extends Formula {
    public Pagie1() {
        vars = 2;
        name = "Pagie1";

        trainingSet = evenlySpaced(-5, 5, 0.4);
        testingSet = evenlySpaced(-5, 5, 0.4);

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
        double x = val[0];
        double y = val[1];
        double ans = 1.0/(1+Math.pow(x,-4))+1.0/(1+Math.pow(y,-4));

        return ans;
    }
}
