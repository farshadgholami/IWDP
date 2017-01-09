package Space;

import Benchmark.Formula.Formula;
import Symbol.GSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farsh on 12/11/2015.
 */
public class Tree extends SpaceStructure{

    int newNodeIndex = 1;

    public Tree(double initLearnValue, Formula formula) {

        super(initLearnValue, formula);

        fillSymbolList();
        initialTree();
    }

    public void initialTree() {
        Node initialNode = new Node(new GSymbol());
        addToNodes(initialNode);

        addFirstLevel(initialNode);
    }

    public void addFirstLevel(Node parentNode) {
        for (GSymbol symbol : symbols) {
            if (!symbol.isTerminal()) {
                Node childrenNode = new Node(symbol);
                addToNodes(childrenNode);
                connectTwoNodes(parentNode, childrenNode);
                childrenNode.setIndexInGraph(newNodeIndex++);
            }
        }
    }

    public void addNewLevel(Node parentNode) {
        for (GSymbol symbol : symbols) {
            Node childrenNode = new Node(symbol);
            addToNodes(childrenNode);
            connectTwoNodes(parentNode, childrenNode);
            childrenNode.setIndexInGraph(newNodeIndex++);
        }
    }

    public void connectTwoNodes(Node firstNode, Node secondNode) {

        GEdge edgeFirstToSecond = new GEdge(firstNode, secondNode, initLearnValue);

        firstNode.addEdge(edgeFirstToSecond);
        firstNode.addNodeToList(secondNode);
    }

    public void reInitial() {
        nodes = new ArrayList();
        edges = new ArrayList();
        newNodeIndex = 1;
        initialTree();
    }

    private void addToNodes(Node node) {
        getNodes().add(node);
    }
}
