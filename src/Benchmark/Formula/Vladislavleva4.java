package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class Vladislavleva4 extends Formula {
    public Vladislavleva4() {
        vars = 5;
        name = "Vladislavleva4";

        trainingSet = uniformRandom(0.05, 6.05, 1024);
        testingSet = uniformRandom(-0.25, 6.35, 5000);

        nonTerminalSet = new GSymbol[]{
                new Plus(),
                new Minus(),
                new Multi(),
                new DivisionProtected(),
                new PowerTwo()
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {

        double sum = 0;
        for(int i = 0; i < 5; i++)
            sum += Math.pow(val[i] - 3, 2);
        return 10.0 / (5.0 + sum);
    }
}
