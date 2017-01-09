package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Pouya Payandeh on 1/26/2016.
 */
public class Pouya1 extends Formula {
    public Pouya1() {
        vars = 1;
        name = "Pouya1";

        trainingSet = evenlySpaced(1, 10, 1);
        testingSet = evenlySpaced(1, 10, 1);

        nonTerminalSet = new GSymbol[]{
                new Negative(),
                new Plus(),
                new Multi(),
                new Div(),
                new Minus()
        };
        constantSet = new GSymbol[]{
                new Constant(1),
                new Constant(2),
                new Constant(3)
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        // double sum = 0;
        double x = Math.floor(val[0]);
        double ans = (x - 1) / (x + 2) + 3;
        return ans;
    }
}
