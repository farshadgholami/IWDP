package Agent;

import Behavior.*;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.Node;
import Space.SpaceStructure;
import Symbol.GSymbol;

import java.util.*;
import java.util.List;

public abstract class Agent {
    Random random = new Random();

    public static double ro = 0.2;
    public static double ros = 0.1;
    public static double rosd = 0.1;

    public void setSelectNextNodeBehavior(SelectNextNodeBehavior selectNextNodeBehavior)
    {
        this.selectNextNodeBehavior = selectNextNodeBehavior;
    }

    SelectNextNodeBehavior selectNextNodeBehavior;

    public void setSelectFirstNodeBehavior(SelectFirstNodeBehavior selectFirstNodeBehavior)
    {
        this.selectFirstNodeBehavior = selectFirstNodeBehavior;
    }

    SelectFirstNodeBehavior selectFirstNodeBehavior = new SelectFirstNode();
    private double TotalItW =1;
    private int lastNodeIndex = -1;
    private int expWeight = 1;

    List<Node> nodePath;
    List<GEdge> edgePath;
    List<Integer> visitedFunctionNodeIndexList;

    private double minLearnValue;
    SpaceStructure spaceStructure;
    Benchmark benchmark;

    private int currentNodeIndex;
    private double fitness;

    public Agent(Agent agent) {
        setFitness(agent.getFitness());
        setTotalItW(agent.getTotalItW());
        setCurrentNodeIndex(agent.getCurrentNodeIndex());
        setLastNodeIndex(agent.getLastNodeIndex());
        setNodePath(cloneNodePath(agent.getNodePath()));
        setEdgePath(cloneEdgePath(agent.getEdgePath()));
        setVisitedFunctionNodeIndexList(cloneVisitedFunctionNodeIndexList(agent.getVisitedFunctionNodeIndexList()));
        setExpWeight(agent.getExpWeight());
        setSpaceStructure(agent.getSpaceStructure());
        setBenchmark(agent.getBenchmark());
        setSelectNextNodeBehavior(agent.selectNextNodeBehavior);
        setSelectFirstNodeBehavior(agent.selectFirstNodeBehavior);
        setMoveBehavior(agent.moveBehavior);
    }

    public Agent(double minLearnValue, SpaceStructure spaceStructure, Benchmark benchmark) {
        setMinLearnValue(minLearnValue);
        setSpaceStructure(spaceStructure);
        setBenchmark(benchmark);
        nodePath = new ArrayList();
        edgePath = new ArrayList();
        visitedFunctionNodeIndexList = new ArrayList();
        selectFirstNode();
     //   this.selectNextNodeBehavior = selectNextNodeBehavior;
    }
    public Agent(double minLearnValue, SpaceStructure spaceStructure, Benchmark benchmark,SelectFirstNodeBehavior selectFirstNodeBehavior) {
        setMinLearnValue(minLearnValue);
        setSpaceStructure(spaceStructure);
        setBenchmark(benchmark);
        nodePath = new ArrayList();
        edgePath = new ArrayList();
        visitedFunctionNodeIndexList = new ArrayList();
        this.selectFirstNodeBehavior=selectFirstNodeBehavior;
        selectFirstNode();
        //   this.selectNextNodeBehavior = selectNextNodeBehavior;
    }

    public void selectFirstNode() {
        assert selectFirstNodeBehavior != null;
        selectFirstNodeBehavior.selectFirstNode(this);
    }

    public void setMoveBehavior(AgentMoveBehavior moveBehavior)
    {
        this.moveBehavior = moveBehavior;
    }

    AgentMoveBehavior moveBehavior = new GraphMove();
    public void move() {
        //move one drop one step
        moveBehavior.move(this);
    }

    public void checkLeastOfLearnValue(GEdge edge) {
        //Agar meghdar har yani ke taghir mikonad az meghdare moshakhas shodeye minLearnValue Kamtar shavad,
        //Meghdare An ra barabar ba minLearnValue mikonim
        if (edge.getLearnValue() < getMinLearnValue()) {
            edge.setLearnValue(getMinLearnValue());
        }
    }

    public abstract void onlineUpdate(GEdge edge);

    private void updateExpWeight(Node node) {
        expWeight += node.getSymbol().getSymbolWeight();
    }

    public abstract int selectNextNode();

    public List<GEdge> neighborEdgeList_GraphMultiSymbol_Creator() {
        Node currentNode = getSpaceStructure().getNode(getCurrentNodeIndex());
        List<Node> neighborNodes = currentNode.getLinkedNodes();
        List<GEdge> neighborEdgeList = new ArrayList<>(currentNode.getEdgesList());
        int neighborSize = neighborEdgeList.size();

        neighborEdgeList(neighborNodes, neighborEdgeList, neighborSize);

        return neighborEdgeList;
    }

    protected void neighborEdgeList(List<Node> neighborNodes, List<GEdge> neighborEdgeList, int neighborSize)
    {
        for (int index = 0; index < neighborSize; index++) {
            int neighborNodeGraphIndex = neighborNodes.get(index).getIndexInGraph();
            if (getLastNodeIndex() == neighborNodeGraphIndex || getVisitedFunctionNodeIndexList().contains(neighborNodeGraphIndex)) {
                neighborNodes.remove(index);
                neighborEdgeList.remove(index);
                //chon yeDune node az list kam shode
                index--;
                neighborSize--;
            }
        }
    }

    public List<GEdge> neighborEdgeList_GraphSingleSymbol_Creator() {
        Node currentNode = getSpaceStructure().getNode(getCurrentNodeIndex());
        List<Node> neighborNodes = currentNode.getLinkedNodes();
        List<GEdge> neighborEdgeList = new ArrayList<>(currentNode.getEdgesList());
        int neighborSize = neighborEdgeList.size();

        //Last Space.Node Neighbor Index
        for (int LNNI = 0; LNNI < neighborSize; ++LNNI) {
            if (getLastNodeIndex() == neighborNodes.get(LNNI).getIndexInGraph()) {
                neighborNodes.remove(LNNI);
                neighborEdgeList.remove(LNNI);
                neighborSize--;
                break;
            }
        }

        return neighborEdgeList;
    }

    public int rouletteWheel(List<GEdge> neighborEdgeList) {
        Node currentNode = getSpaceStructure().getNode(getCurrentNodeIndex());
        double nextP = random.nextDouble();
        int neighborSize = neighborEdgeList.size();
        double[] p = new double[neighborSize];
        int nextNode = 0;
        List<GEdge> edgelist = currentNode.getEdgesList();
        for (int i = 0; i < neighborSize; i++) {
            p[i] = proportionalPossibility(i, neighborEdgeList);
        }

        for (int i = 0; i < (neighborSize - 1); ++i)
            p[i + 1] += p[i];

        for (int i = 0; i < neighborSize; ++i) {
            p[i] /= p[neighborSize - 1];
        }

        for (int i = 0; i < neighborSize; ++i) {
            if (p[i] >= nextP) {
                //nextNode = i;
                nextNode = edgelist.indexOf(neighborEdgeList.get(i));
                //nextNode = currentNode.getLinkedNodes().indexOf(neighborNodes.get(i));
                break;
            }
        }

        return nextNode;
    }

    public abstract double proportionalPossibility(int secondNodeIndex, List<GEdge> neighborEdgeList);

    private double calPathOutput(double[] variableValue, List<Node> nodePath) {

        if (getExpWeight() == 0) {
            GSymbol symbol;
            if (nodePath.isEmpty())
                return 0;
            symbol = nodePath.remove(0).getSymbol();
            int numOprand = symbol.getNamOperand();

            double exprResult = 0;

            switch (numOprand) {
                case 2:
                    exprResult = symbol.exe(calPathOutput(variableValue, nodePath), calPathOutput(variableValue, nodePath));
                    break;
                case 1:
                    exprResult = symbol.exe(calPathOutput(variableValue, nodePath));
                    break;
                case 0:
                    if (symbol.isConstant()) {
                        exprResult = symbol.exe();
                    } else {
                        exprResult = symbol.exe(variableValue[symbol.getVariableNum() - 1]);
                    }
                    break;
                case -1:
                    exprResult = calPathOutput(variableValue, nodePath);
                    break;

            }
            return exprResult;
        } else
            return Double.MAX_VALUE;
    }

    public String inFixPath(List<Node> nodePath) {

        String o="";
        if (getExpWeight() == 0) {
            GSymbol symbol;
            if (nodePath.isEmpty())
                return "";
            symbol = nodePath.remove(0).getSymbol();
            int numOprand = symbol.getNamOperand();


            switch (numOprand) {
                case 2:
                    o ="("+ inFixPath(nodePath) +") " +symbol.getPresentation()+ " (" +inFixPath(nodePath)+")";
                    break;
                case 1:
                    String oo =inFixPath(nodePath);
                    if(symbol.isFunc())
                        o = symbol.getPresentation() + " [" + oo+"]";
                    else
                        o = symbol.getPresentation() + " (" + oo+")";
                    break;
                case 0:
                    if (symbol.isConstant()) {
                        o = symbol.getPresentation();
                    } else {
                        o = symbol.getPresentation();
                    }
                    break;
                case -1:
                    o = inFixPath(nodePath);
                    break;

            }
            return o;
        } else
            return "Bad Value";
    }
    public double calPathFitness() {

        int trainSetSize = benchmark.getFormula().getTrainingSetSize();
        double expressionOut;
        ArrayList<Double> values = new ArrayList();
        for (int index = 0; index < trainSetSize; index++) {

            double[] trainingValues = benchmark.getFormula().getTrainingValues(index);

            expressionOut = calPathOutput(trainingValues, new LinkedList<>(getNodePath()));
            values.add(expressionOut);
        }
        setFitness(benchmark.getFitness(values));
        return getFitness();
    }

    public double calTest() {
        int testSetSize = benchmark.getFormula().getTestingSetSize();

        double sumOfSquare = 0;
        double expectedOutput;
        double expressionOut;
        for (int index = 0; index < testSetSize; index++) {

            double[] testingValues = benchmark.getFormula().getTestingValues(index);

            expressionOut = calPathOutput(testingValues, cloneNodePath(getNodePath()));
            expectedOutput = benchmark.getFormula().getExpectedTestResult(index);

            sumOfSquare += Math.pow(expectedOutput - expressionOut, 2);
        }
        return Math.sqrt(sumOfSquare);
    }

    public void addToPath(Node node, GEdge edge) {
        addNodeToPath(node);
        addEdgeToPath(edge);
        updateExpWeight(node);
        setLastNodeIndex(getCurrentNodeIndex());
        setCurrentNodeIndex(node.getIndexInGraph());
        addVisitedFunctionNodeIndex(getCurrentNodeIndex());
    }

    public void addToPath(Node node) {
        addNodeToPath(node);
        updateExpWeight(node);
        setCurrentNodeIndex(node.getIndexInGraph());
        addVisitedFunctionNodeIndex(getCurrentNodeIndex());
    }

    private void addNodeToPath(Node node) {
        nodePath.add(node);
    }

    private void addEdgeToPath(GEdge edge) {
        edgePath.add(edge);
    }

    private void addVisitedFunctionNodeIndex(int index) {
        if (!spaceStructure.getNode(index).getSymbol().isTerminal()) {
            visitedFunctionNodeIndexList.add(index);
        }
    }

    public void reInitial() {
        nodePath = new ArrayList<>();
        edgePath = new ArrayList<>();
        visitedFunctionNodeIndexList = new ArrayList<>();
        setLastNodeIndex(-1);
        ++TotalItW;
        setExpWeight(1);

        selectFirstNode();
    }

    public void printPath() {
        for (Node node : getNodePath()) {
            System.out.print(node.getSymbol().getPresentation() + " ");
        }
        System.out.println();
    }
    public void printInFixPath()
    {
       System.out.println(inFixPath(cloneNodePath(getNodePath())));
    }
    /*************Set Get**************************/

    public double getFitness() {
        return fitness;
    }

    public double getTotalItW() {
        return TotalItW;
    }

    public int getCurrentNodeIndex() {
        return currentNodeIndex;
    }

    public int getLastNodeIndex() {
        return lastNodeIndex;
    }

    public List<GEdge> getEdgePath() {
        return edgePath;
    }

    public List<Node> getNodePath() {
        return nodePath;
    }

    public List<Integer> getVisitedFunctionNodeIndexList() {
        return visitedFunctionNodeIndexList;
    }

    public int getExpWeight() {
        return expWeight;
    }

    public SpaceStructure getSpaceStructure() {
        return spaceStructure;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public double getMinLearnValue() {
        return minLearnValue;
    }


    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setTotalItW(double totalItW) {
        TotalItW = totalItW;
    }

    public void setCurrentNodeIndex(int currentNodeIndex) {
        this.currentNodeIndex = currentNodeIndex;
    }

    public void setLastNodeIndex(int lastNodeIndex) {
        this.lastNodeIndex = lastNodeIndex;
    }

    public void setNodePath(List<Node> nodePath) {
        this.nodePath = nodePath;
    }

    public void setEdgePath(List<GEdge> edgePath) {
        this.edgePath = edgePath;
    }

    public void setVisitedFunctionNodeIndexList(List<Integer> visitedFunctionNodeIndexList) {
        this.visitedFunctionNodeIndexList = new ArrayList<>(visitedFunctionNodeIndexList);
    }

    public void setExpWeight(int expWeight) {
        this.expWeight = expWeight;
    }

    public void setSpaceStructure(SpaceStructure spaceStructure) {
        this.spaceStructure = spaceStructure;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public void setMinLearnValue(double minLearnValue) {
        this.minLearnValue = minLearnValue;
    }

    /*********Clone***********/

    public List<GEdge> cloneEdgePath(List<GEdge> edgePath) {
        return new ArrayList<>(edgePath);
    }

    public List<Node> cloneNodePath(List<Node> nodePath) {
        return new ArrayList<>(nodePath);
    }

    public List<Integer> cloneVisitedFunctionNodeIndexList(List<Integer> visitedFunctionNodeIndexList) {
        return new ArrayList<>(visitedFunctionNodeIndexList);
    }
}
