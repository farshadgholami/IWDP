package Space;

import Benchmark.Formula.Formula;

/**
 * Created by farsh on 12/12/2015.
 */
public class GraphSingleSymbol extends Graph {
    public GraphSingleSymbol(double initLearnValue, Formula formula, int countOfEachSymbol) {
        super(initLearnValue, formula, countOfEachSymbol);
        fillSymbolList();
        //this.numberOfNodes = X.size() + F.size();
        this.numberOfNodes = getSymbols().size();
    }
}
