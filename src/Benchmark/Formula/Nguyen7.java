package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Pouya Payandeh on 2/4/2016.
 */
public class Nguyen7 extends Formula
{
    public Nguyen7() {
        vars = 1;
        name = "Nguten7";

        trainingSet = uniformRandom(0, 2, 20);
        testingSet = trainingSet;

        nonTerminalSet = new GSymbol[]{
                new Plus(),
                new Minus(),
                new Multi(),
                new DivisionProtected(),
                new Sin(),
                new Cos(),
                new Exponential(),
                new LogProtected()
        };

        calcuteNumberOfNode();
        calculateExpectedResult();
    }

    public double objectiveFunction(double[] val) {
        double x = val[0];
        double ans = Math.log(x+1)+Math.log(x*x+1);

        return ans;
    }
}
