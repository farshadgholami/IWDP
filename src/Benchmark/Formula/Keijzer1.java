package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class Keijzer1 extends Formula {

    public Keijzer1() {

        vars = 1;
        name = "Keijzer6";

        trainingSet = evenlySpaced(-1, 1, 0.1);
        testingSet = evenlySpaced(-1, 1, 0.001);

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
        double x =  val[0];
        return 0.3 * x * Math.sin(2 * Math.PI * x);
    }
}
