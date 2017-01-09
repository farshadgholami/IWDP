package Space;

import Benchmark.Formula.Formula;

/**
 * Created by farsh on 12/12/2015.
 */
public class RandomGraphMultiSymbol extends GraphMultiSymbol {
    public RandomGraphMultiSymbol(double initLearnValue, Formula formula, int countOfEachSymbol) {
        super(initLearnValue, formula, countOfEachSymbol);

        createRandomGraph();

        checkGraph();
    }
}
