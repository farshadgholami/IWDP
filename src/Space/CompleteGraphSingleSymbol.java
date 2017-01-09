package Space;

import Benchmark.Formula.Formula;

/**
 * Created by farsh on 12/12/2015.
 */
public class CompleteGraphSingleSymbol extends GraphSingleSymbol {
    public CompleteGraphSingleSymbol(double initLearnValue, Formula formula, int countOfEachSymbol) {
        super(initLearnValue, formula, countOfEachSymbol);

        createCompleteGraph();
    }
}
