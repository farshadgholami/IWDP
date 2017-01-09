package Space;

import Symbol.GSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farshad on 11/2/15.
 */
public class Node {

    int indexInGraph;
    GSymbol symbol;
    List<GEdge> edgesList;
    List<Node> linkedNodes;

    int numberOfTerminal = 0;

    public Node(GSymbol symbol) {
        this.symbol = symbol;
        linkedNodes = new ArrayList();
        edgesList = new ArrayList();
    }

    public boolean hasTwoTerminal() {
        if (numberOfTerminal >= 2)
            return true;
        else
            return false;
    }

    public void addTerminal() {
        numberOfTerminal++;
    }

    public void addNodeToList(Node node) {
        linkedNodes.add(node);
    }

    public void addEdge(GEdge edge) {
        edgesList.add(edge);
    }

    public List<GEdge> getEdgesList() {
        return edgesList;
    }

    public GEdge getEdge(int index) {
        return edgesList.get(index);
    }

    public List<Node> getLinkedNodes() {
        return new ArrayList<>(linkedNodes);
    }

    public Node getLinkedNode(int index) {
        return linkedNodes.get(index);
    }

    public GSymbol getSymbol() {
        return symbol;
    }

    public void setIndexInGraph(int index) {
        indexInGraph = index;
    }

    public int getIndexInGraph() {
        return indexInGraph;
    }
}
