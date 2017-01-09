package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class Keijzer6 extends Formula {

    public Keijzer6() {
        vars = 1;
        name = "Keijzer6";

        trainingSet = evenlySpaced(1, 50, 1);
        testingSet = evenlySpaced(1, 120, 1);

        nonTerminalSet = new GSymbol[]{
                new Negative(),
                new Plus(),
                new Multi(),
                new OneOnN(),
                new Sqrt()
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        double sum = 0;
        double fx = Math.floor(val[0]);
        for(int i = 1; i < fx + 1; i++)
            sum += (1.0 / i);
        return sum;
    }
}
