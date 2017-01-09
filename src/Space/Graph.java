package Space;

import Benchmark.Formula.Formula;
import Symbol.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Farshad PC on 9/18/2015.
 */

public class Graph extends SpaceStructure{

    List<Node> terminalNodeList;

    //GSymbol[] x = new GSymbol[5];
    List<GSymbol> X = new ArrayList();
    List<GSymbol> F = new ArrayList();

    int numberOfNodes;

    int countOfEachSymbol;

    Random random = new Random();

    public Graph(double initLearnValue, Formula formula, int countOfEachSymbol) {

        super(initLearnValue, formula);

        this.countOfEachSymbol = countOfEachSymbol;
        terminalNodeList = new ArrayList<>();

        /*
        * Tuye Raveshe newWay, az har symbol chand ta misaze, vali tuye oldWay faghat yedune misaze.
        * Ba tavajjoh be inke ma 2 no raveshe vasl shodane nodeHara darim, dar kol 4No mishe graph dasht
        * 1. graph kameli ke az har symbol 1 node darim
        * 2. graph randomi ke ke az har symbol 1 node darim
        * 3. graph kameli ke az har symbol be teedade countOfEachSymbol node darim
        * 4. graph randomi ke az har symbol be teedade countOfEachSymbol node darim
         */
        //Noe 1
        //oldWayCompleteGraph();
        //Noe 2
        //oldWayRandomGraph();
        //Noe 3
        //newWayCompleteGraph();
        //Noe 4
        //newWayRandomGraph();
    }

    /*public void oldWayRandomGraph() {
        oldFillSymbolList();
        //this.numberOfNodes = X.size() + F.size();
        this.numberOfNodes = symbols.size();

        createRandomGraph();
    }

    public void oldWayCompleteGraph() {
        oldFillSymbolList();
        //this.numberOfNodes = X.size() + F.size();
        this.numberOfNodes = symbols.size();

        createCompleteGraph();
    }

    public void newWayRandomGraph() {
        fillSymbolList();
        this.numberOfNodes = symbols.size();
        createRandomGraph();

        checkGraph();
    }

    public void newWayCompleteGraph() {
        fillSymbolList();
        this.numberOfNodes = symbols.size();
        createCompleteGraph();

        checkGraph();
    }*/

    public void createRandomGraph() {

        Node node;
        getNodes().add(createNode());

        for (int i = 1; i < numberOfNodes; i++) {
            node = createNode();

            //graph random
            connectNodes(node, randomSelectIndexes());
            getNodes().add(node);
            node.setIndexInGraph(i);
        }
    }

    public void createCompleteGraph() {

        Node node;
        getNodes().add(createNode());

        for (int i = 1; i < numberOfNodes; i++) {
            node = createNode();

            //graph kamel
            connectNodes(node);

            getNodes().add(node);
            node.setIndexInGraph(i);
        }
    }

    public void oldCreateGraph() {

        Node node;
        getNodes().add(oldCreateNode());

        for (int i = 1; i < numberOfNodes; i++) {

            node = oldCreateNode();

            //i = number of nodes in the graph
            //graph kamel
            connectNodes(node);
            //graph random
            //connectNodes(node, randomSelectIndexes());
            getNodes().add(node);
            node.setIndexInGraph(i);
        }
    }

    public void connectNodes(Node firstNode) {
        //In ravesh miad kolli node ro be surate random tolid
        //mikone, vali hamashuno beham vasl mikone
        for (Node secondNode : getNodes()) {
            connectTwoNodes(firstNode, secondNode);
        }
    }

    public void connectNodes(Node node, List<Integer> indexes) {
        //In ravesh miad kolli node ro be surate random tolid
        //mikone, vali be surate randome baraye vasl kardane node
        //Ha tasmim migire
        while (!indexes.isEmpty()) {
            connectTwoNodes(node, getNodes().get(indexes.remove(0)));
        }
    }

    public List<Integer> randomSelectIndexes() {

        List<Integer> selectedIndexes = new ArrayList<>();
        int nodesNeeded = random.nextInt(getNodes().size()) + 1;

        while (selectedIndexes.size() < nodesNeeded) {
            int pickedNumber = random.nextInt(getNodes().size());
            if (!selectedIndexes.contains(pickedNumber)) {
                selectedIndexes.add(pickedNumber);
            }
        }
        return selectedIndexes;
    }

    public void connectTwoNodes(Node firstNode, Node secondNode) {

        GEdge edgeFirstToSecond = new GEdge(firstNode, secondNode, initLearnValue);
        GEdge edgeSecondToFirst = new GEdge(secondNode, firstNode, initLearnValue);

        //in method vase oun moshkel hastesh ke age har node haddeaghal daraye
        //yek terminal nabashad momken ast ghatarat dar masire harekate khod
        //be dalil dashtane tabuList, dochate bonbast shavand
        if (secondNode.getSymbol().isTerminal()) {
            firstNode.addTerminal();
        }
        if (firstNode.getSymbol().isTerminal()) {
            secondNode.addTerminal();
        }

        addToEdges(edgeFirstToSecond);
        addToEdges(edgeSecondToFirst);

        firstNode.addEdge(edgeFirstToSecond);
        secondNode.addEdge(edgeSecondToFirst);
        firstNode.addNodeToList(secondNode);
        secondNode.addNodeToList(firstNode);
    }

    public Node createNode() {
        Node node;
        int rand = random.nextInt(symbols.size());

        node = new Node(symbols.remove(rand));

        if (node.getSymbol().isTerminal()) {
            terminalNodeList.add(node);
        }

        return node;
    }

    public Node oldCreateNode() {
        Node node;
        if (!F.isEmpty()) {
            node = new Node(F.remove(0));
        } else if (!X.isEmpty()) {
            node = new Node(F.remove(0));
        } else {
            node = new Node(new KeijzerConstant());
        }

        return node;
    }

    private void addToEdges(GEdge edge) {
        getEdges().add(edge);
    }


    /***********Get & Set*********/

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public GEdge getEdge(Node firstNode, Node secondNode) {

        GEdge edge = null;
        for (int i = 0; i < firstNode.getEdgesList().size(); i++)
            if (firstNode.getEdge(i).getSecondNode().equals(secondNode)) {
                edge = firstNode.getEdge(i);
                break;
            }

        return edge;
    }

    public void reInitial() {
        for (int i = 0; i < getNodes().size() - 1; i++) {
            for (int j = 0; j < getNode(i).getEdgesList().size(); j++) {
                getNodes().get(i).getEdge(j).setLearnValue(initLearnValue);
            }
        }
    }
}

