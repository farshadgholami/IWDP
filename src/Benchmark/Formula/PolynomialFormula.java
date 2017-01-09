package Benchmark.Formula;

import Symbol.*;

/**
 * Created by Farshad PC on 10/5/2015.
 */
public class PolynomialFormula extends Formula {
    Function function;
    public PolynomialFormula(Function f, double start, double end, double step) {
        function = f;
        vars = 1;
        name = "Polynomial";

        trainingSet = evenlySpaced(start, end, step);
        testingSet = evenlySpaced(start, end, step);

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
        return function.f(val[0]);
    }
}
