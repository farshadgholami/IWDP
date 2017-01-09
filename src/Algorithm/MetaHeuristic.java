package Algorithm;

import Agent.Agent;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.Node;
import Space.SpaceStructure;

import java.util.ArrayList;
import java.util.List;

public abstract class MetaHeuristic {

    private double bestGlobalFitAllRunWithTrain = Double.MAX_VALUE;
    private double bestGlobalFitAllRunWithTest = Double.MAX_VALUE;
    Agent bestGlobalAgentAllRunByTrain;
    Agent bestGlobalAgentAllRunByTest;
    private double mean;

    private int globBestAgentIndex = 0;
    private double globBestAgentFit = Double.MAX_VALUE;
    private double sumGlobalRunBestFit;

    List<Node> bestNodePath;
    List<GEdge> bestEdgePath;
    Agent bestAgent;

    public static double ro = 0.2;
    public static double ros = 0.1;
    public static double rosd = 0.1;

    private double minLearnValue;
    private int sequenceLength = 1000;
    private int noIteration = 100;
    public static int reInitialItrNo = 100;
    private int numberOfRun;

    private SpaceStructure spaceStructure;
    private Agent[] agents;
    Benchmark benchmark;

    public MetaHeuristic(SpaceStructure spaceStructure, Agent[] agents, double minLearnValue, Benchmark benchmark) {
        setSpaceStructure(spaceStructure);
        setAgents(agents);
        setMinLearnValue(minLearnValue);
        noIteration = benchmark.getIterationsN();
        numberOfRun = benchmark.getRunsN();
        this.benchmark = benchmark;
        run();
    }

    public void run(){
        setSumGlobalRunBestFit(0);

        for (int j = 0; j < getNumberOfRun(); j++) {
            if (j != 0) reInitialRunInfo();
            for (int i = 0; i < getNoIteration(); ++i) {
                startIter();
                if (i % getReInitialItrNo() == 0 && i != 0)
                    getSpaceStructure().reInitial();
            }
            getSpaceStructure().reInitial();
            if (getBestAgent() != null)
                checkForBestGlobal();
        }
    }

    private void reInitialRunInfo() {
        getSpaceStructure().reInitial();
        setGlobBestAgentIndex(0);
        setGlobBestAgentFit(Double.MAX_VALUE);

        setBestNodePath(new ArrayList<>());
        setBestEdgePath(new ArrayList<>());
        setBestAgent(null);
    }

    private void startIter() {
        /*Start moving*/
        moveAgents();

        /*keep best*/
        findBestIterSolution();

        /*reinitialise*/
        reInitAgents();
    }

    public void moveAgents() {
        boolean[] aFlag = new boolean[getAgents().length];
        boolean moveNotTerminate = true;

        /*start search of other node*/
        while (moveNotTerminate) {
            moveNotTerminate = false;
            for (int i = 0; i < getAgents().length; ++i) {
                if (!aFlag[i]) {
                    moveNotTerminate = true;

                    //moveOneDrop(myDrop);
                    getAgent(i).move();

                    if (getAgent(i).getExpWeight() == 0)
                        aFlag[i] = true;

                    /** Over sequence */
                    else if (getAgent(i).getNodePath().size() > getSequenceLength()) {
                        aFlag[i] = true;
                    }
                }
            }
        }
    }

    public void findBestIterSolution() {
        int itrBestAgentIndex;
        double itrBestAgentFit;
        double[] fit = new double[getAgents().length];
        ArrayList<Integer> idxs = new ArrayList<>();
        for (int i = 0; i < getAgents().length; ++i) {
            fit[i] = getAgent(i).calPathFitness();
            idxs.add(i);
        }

        idxs.sort((o1, o2) -> Double.compare(fit[o1],fit[o2]));
        itrBestAgentIndex=idxs.get(0);
        itrBestAgentFit=fit[itrBestAgentIndex];

        if (benchmark.isUseSF())
            HeuristicInformation.updateSF(idxs,getAgents());

        if (benchmark.isUseHUD())
            HUD.updateHUD(idxs,getAgents());

        offlineUpdate(itrBestAgentIndex);

        if (itrBestAgentFit < getGlobBestAgentFit()) {
            setBestResult(itrBestAgentIndex, itrBestAgentFit);
        }

        printIterationResult(itrBestAgentFit);
    }

    public abstract void offlineUpdate(int bestAgentIndex);

    public void checkLeastOfLearnValue(GEdge edge) {
        //Agar meghdar har yani ke taghir mikonad az meghdare moshakhas shodeye minLearnValue Kamtar shavad,
        //Meghdare An ra barabar ba minLearnValue mikonim
        if (edge.getLearnValue() < getMinLearnValue()) {
            edge.setLearnValue(getMinLearnValue());
        }
    }

    private void reInitAgents() {
        for (Agent agent : getAgents()) {
            agent.reInitial();
        }
    }

    public void checkForBestGlobal() {
        if (getGlobBestAgentFit() < getBestGlobalFitAllRunWithTrain()) {
            setBestGlobalFitAllRunWithTrain(getGlobBestAgentFit());
            setBestGlobalAgentAllRunByTrain(cloneAgent(getBestAgent()));
        }

        double temp = getBestAgent().calTest();
        if (temp < getBestGlobalFitAllRunWithTest()) {
            setBestGlobalFitAllRunWithTest(temp);
            setBestGlobalAgentAllRunByTest(cloneAgent(getBestAgent()));
        }
    }

    public void printBestResult() {
        System.out.println("Global Best change to: " + getGlobBestAgentFit());
        System.out.println(getBestAgent().getNodePath().size());
        Agent best = getBestAgent();
        best.printInFixPath();
    }

    public String getBestPath() {
        Agent best = getBestGlobalAgentAllRunByTest();
        return best.inFixPath(cloneNodePath(best.getNodePath()));
    }
    public void printIterationResult(double itrBestDropFit) {
//        for (int index = 0; index < getAgents().length; index++) {
//            System.out.println("Fit of Drop: " + index + " --> " + getAgent(index).getFitness());
//            System.out.println("Path of Drop: ");
//            getAgent(index).printPath();
//            getAgent(index).printInFixPath();
//
//        }
//
//        if (itrBestDropFit < getGlobBestAgentFit()) {
//            printBestResult();
//        }
//        else {
//            System.out.println("Iteration best Fit: " + itrBestDropFit);
//        }
    }

    public void printEachRunBestResult() {
//        System.out.println("\nGlobal Best for Train Value: " + getGlobBestAgentFit());
//        System.out.println("Best Test Value: " + getBestAgent().calPathFitness());
//        getBestAgent().printInFixPath();
//        for (Node node : getBestAgent().getNodePath()) {
//            System.out.print(node.getSymbol().getPresentation() + " ");
//        }
    }

    public void printLastBestResult() {
//        System.out.println("\n\nGlobal Best for Train Value: " + bestGlobalFitAllRunWithTrain);
//        System.out.println("Best Test Value: " + bestGlobalFitAllRunWithTest);
//        System.out.println("Mean of Best Global Fit in some Run: " + mean);
//        System.out.println("\nBest path of Train:");
//        getBestGlobalAgentAllRunByTrain().printInFixPath();
//        for (Node node : ).getNodePath()) {
//            System.out.print(node.getSymbol().getPresentation() + " ");
//        }
//        System.out.println("\nBest path of Test:");
//        getBestGlobalAgentAllRunByTest().printInFixPath();
//        for (Node node : getBestGlobalAgentAllRunByTest().getNodePath()) {
//            System.out.print(node.getSymbol().getPresentation() + " ");
//        }
    }

    void setBestResult(int agentIndex, double agentFitness) {
        setGlobBestAgentFit(agentFitness);
        setGlobBestAgentIndex(agentIndex);
        setBestAgent(cloneAgent(getAgent(agentIndex)));
        setBestEdgePath(cloneEdgePath(getAgent(agentIndex).getEdgePath()));
        setBestNodePath(cloneNodePath(getAgent(agentIndex).getNodePath()));
    }

    public void setSumGlobalRunBestFit(double sumGlobalRunBestFit) {
        this.sumGlobalRunBestFit = sumGlobalRunBestFit;
    }

    public void setBestGlobalFitAllRunWithTrain(double bestGlobalFitAllRunWithTrain) {
        this.bestGlobalFitAllRunWithTrain = bestGlobalFitAllRunWithTrain;
    }

    public void setBestGlobalFitAllRunWithTest(double bestGlobalFitAllRunWithTest) {
        this.bestGlobalFitAllRunWithTest = bestGlobalFitAllRunWithTest;
    }

    public void setBestGlobalAgentAllRunByTrain(Agent bestGlobalAgentAllRunByTrain) {
        this.bestGlobalAgentAllRunByTrain = bestGlobalAgentAllRunByTrain;
    }

    public void setBestGlobalAgentAllRunByTest(Agent bestGlobalAgentAllRunByTest) {
        this.bestGlobalAgentAllRunByTest = bestGlobalAgentAllRunByTest;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setGlobBestAgentFit(double globBestAgentFit) {
        this.globBestAgentFit = globBestAgentFit;
    }

    public void setBestNodePath(List<Node> bestNodePath) {
        this.bestNodePath = bestNodePath;
    }

    public void setBestEdgePath(List<GEdge> bestEdgePath) {
        this.bestEdgePath = bestEdgePath;
    }

    public void setBestAgent(Agent bestAgent) {
        this.bestAgent = bestAgent;
    }

    public void setMinLearnValue(double minLearnValue) {
        this.minLearnValue = minLearnValue;
    }

    public void setSequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public void setNoIteration(int noIteration) {
        this.noIteration = noIteration;
    }

    public void setReInitialItrNo(int reInitialItrNo) {
        this.reInitialItrNo = reInitialItrNo;
    }

    public void setNumberOfRun(int numberOfRun) {
        this.numberOfRun = numberOfRun;
    }

    public void setSpaceStructure(SpaceStructure spaceStructure) {
        this.spaceStructure = spaceStructure;
    }

    public void setAgents(Agent[] myAgents) {
        this.agents = myAgents;
    }

    public void setGlobBestAgentIndex(int globBestAgentIndex) {
        this.globBestAgentIndex = globBestAgentIndex;
    }

    /************Get*************/

    public String getBestGlobalPathByTrain() {
        String path = "";
        for (Node node : getBestGlobalAgentAllRunByTrain().getNodePath()) {
            path += node.getSymbol().getPresentation() + " ";
        }
        return path;
    }

    public String getBestGlobalPathByTest() {
        String path = "";
        for (Node node : getBestGlobalAgentAllRunByTest().getNodePath()) {
            path += node.getSymbol().getPresentation() + " ";
        }
        return path;
    }

    public Agent getBestGlobalAgentAllRunByTrain() {
        return this.bestGlobalAgentAllRunByTrain;
    }

    public Agent getBestGlobalAgentAllRunByTest() {
        return this.bestGlobalAgentAllRunByTest;
    }

    public double getBestGlobalFitAllRunWithTrain() {
        return bestGlobalFitAllRunWithTrain;
    }

    public double getBestGlobalFitAllRunWithTest() {
        return bestGlobalFitAllRunWithTest;
    }

    public Agent getBestAgent() {
        return this.bestAgent;
    }

    public double getGlobBestAgentFit() {
        return globBestAgentFit;
    }

    public double getSumGlobalRunBestFit() {
        return sumGlobalRunBestFit;
    }

    public int getNumberOfRun() {
        return numberOfRun;
    }

    public int getNoIteration() {
        return noIteration;
    }

    public int getReInitialItrNo() {
        return reInitialItrNo;
    }

    public SpaceStructure getSpaceStructure() {
        return spaceStructure;
    }

    public double getMean() {
        return mean;
    }

    public int getGlobBestAgentIndex() {
        return globBestAgentIndex;
    }

    public List<Node> getBestNodePath() {
        return bestNodePath;
    }

    public List<GEdge> getBestEdgePath() {
        return bestEdgePath;
    }

    public double getRo() {
        return ro;
    }

    public double getRos() {
        return ros;
    }

    public double getRosd() {
        return rosd;
    }

    public double getMinLearnValue() {
        return minLearnValue;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public Agent[] getAgents() {
        return agents;
    }

    public Agent getAgent(int index) {
        return agents[index];
    }

    /*********Clone************/

    public abstract Agent cloneAgent(Agent bestAgent);

    public abstract Agent[] cloneAgents(Agent[] myAgents);

    public List<Node> cloneNodePath(List<Node> bestNodePath) {
        return new ArrayList(bestNodePath);
    }

    public List<GEdge> cloneEdgePath(List<GEdge> bestEdgePath) {
        return new ArrayList(bestEdgePath);
    }
}
