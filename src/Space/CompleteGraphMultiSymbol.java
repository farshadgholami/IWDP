package Space;

import Benchmark.Formula.Formula;

/**
 * Created by farsh on 12/12/2015.
 */
public class CompleteGraphMultiSymbol extends GraphMultiSymbol {
    public CompleteGraphMultiSymbol(double initLearnValue, Formula formula, int countOfEachSymbol) {
        super(initLearnValue, formula, countOfEachSymbol);

        createCompleteGraph();

        checkGraph();
    }
}
