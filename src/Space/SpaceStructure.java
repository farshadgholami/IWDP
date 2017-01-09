package Space;

import Benchmark.Formula.Formula;
import Symbol.GSymbol;
import Symbol.KeijzerConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by farsh on 12/12/2015.
 */
public class SpaceStructure {
    List<Node> nodes;
    List<GEdge> edges;

    //list of all symbol
    List<GSymbol> symbols = new ArrayList<>();

    int numberOfVariable;

    double initLearnValue;

    Formula formula;

    GSymbol[] functions;

    public SpaceStructure(double initLearnValue, Formula formula) {
        this.initLearnValue = initLearnValue;
        this.formula = formula;
        getBenchmarkProperty();

        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void fillSymbolList() {

        for (int i = 1; i <= getNumberOfVariable(); i++) {
            GSymbol x = new GSymbol(i);
            getSymbols().add(x);
        }

        Collections.addAll(getSymbols(), getFunctions());

        for (int count = 0; count < formula.getNumberOfConstant(); count++) {
            getSymbols().add(formula.getConstant(count));
        }

        if (getFormula().isKeijzer()) {
            getSymbols().add(new KeijzerConstant());
        }
    }

    public void connectTwoNodes(Node firstNode, Node secondNode){}

    public void getBenchmarkProperty() {
        functions = getFormula().getNonTerminalSet();
        numberOfVariable = getFormula().getVars();
    }

    public void reInitial(){}

    public void printEdgeValues() {
        for (Node node : getNodes()) {
            for (int i = 0; i < node.getEdgesList().size(); i++) {
                System.out.print(node.getEdge(i).getLearnValue() + " ");
            }
            System.out.println();
        }
    }

    public void printSpaceStructure() {
        for (Node node : getNodes()) {
            System.out.print(node.getSymbol().getPresentation() + ": ");
            for (Node n : node.getLinkedNodes()) {
                System.out.print(n.getSymbol().getPresentation() + " ");
            }
            System.out.println();
        }
    }

    /***************Get And Set***************/

    public List<Node> getNodes() {
        return nodes;
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public List<GEdge> getEdges() {
        return edges;
    }

    public GEdge getEdge(int index) {
        return edges.get(index);
    }

    public List<GSymbol> getSymbols() {
        return symbols;
    }

    public int getNumberOfVariable() {
        return numberOfVariable;
    }

    public double getInitLearnValue() {
        return initLearnValue;
    }

    public Formula getFormula() {
        return formula;
    }

    public GSymbol[] getFunctions() {
        return functions;
    }
}
