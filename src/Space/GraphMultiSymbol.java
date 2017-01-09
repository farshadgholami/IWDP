package Space;

import Benchmark.Formula.Formula;
import Symbol.GSymbol;
import Symbol.KeijzerConstant;

/**
 * Created by farsh on 12/12/2015.
 */
public class GraphMultiSymbol extends Graph {
    public GraphMultiSymbol(double initLearnValue, Formula formula, int countOfEachSymbol) {
        super(initLearnValue, formula, countOfEachSymbol);

        fillSymbolList();
        this.numberOfNodes = getSymbols().size();
    }

    public void fillSymbolList() {
        //be tedade variable Hayi kedarim moteghayer misaze va har kodum ro
        //be andazeye countOfEachSymbol bar dakhele list symbols add mikone
        for (int i = 1; i <= numberOfVariable; i++) {
            GSymbol x = new GSymbol(i);

            for (int count = 0; count < countOfEachSymbol; count++) {
                getSymbols().add(new GSymbol(x));
            }
        }

        //haryek az function Haro be tedade countOfEachSymbol bar dakhele
        //symbols add mikone
        for (GSymbol function : functions) {
            for (int count = 0; count < countOfEachSymbol; count++) {
                getSymbols().add(function);
            }
        }

        //age formula niaz be constant dasht, be symbol be tedade countOfEachSymbol
        //bar constant ezafe mikone (constantHaye motefaveti eijad mikone

        for (int count = 0; count < formula.getNumberOfConstant(); count++) {
            for (int i = 0; i < countOfEachSymbol; i++) {
                getSymbols().add(formula.getConstant(count));
            }
        }

        if (getFormula().isKeijzer()) {
            for (int count = 0; count < countOfEachSymbol; count++) {
                getSymbols().add(new KeijzerConstant());
            }
        }
    }

    public void checkGraph() {
        for (Node node : getNodes()) {
            while (!node.hasTwoTerminal()) {
                addTerminalToNode(node);
            }
        }
    }

    public void addTerminalToNode(Node node) {
        Node terminalNode;
        do {
            int rand = random.nextInt(terminalNodeList.size());
            terminalNode = terminalNodeList.get(rand);

        } while (node == terminalNode || node.getLinkedNodes().contains(terminalNode));

        connectTwoNodes(node, terminalNode);
    }
}
