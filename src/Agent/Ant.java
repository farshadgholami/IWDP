package Agent;

import Behavior.AntNSelectNextNode;
import Behavior.SelectFirstNodeBehavior;
import Behavior.SelectNextNodeBehavior;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.Node;
import Space.SpaceStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by farsh on 12/7/2015.
 */
public class Ant extends Agent {

    //acs -> q_0 = 0.9
    double qZero = 0.6;
    double beta = 1;

    public Ant(Agent agent) {
        super(agent);
    }

    public Ant(double minLearnValue, SpaceStructure spaceStructure, Benchmark formula, SelectNextNodeBehavior selectNextNodeBehavior) {
        super(minLearnValue, spaceStructure, formula);
        setSelectNextNodeBehavior(selectNextNodeBehavior);
    }
    public Ant(double minLearnValue, SpaceStructure spaceStructure, Benchmark formula, SelectNextNodeBehavior selectNextNodeBehavior
    , SelectFirstNodeBehavior selectFirstNodeBehavior) {
        super(minLearnValue, spaceStructure, formula,selectFirstNodeBehavior);
        setSelectNextNodeBehavior(selectNextNodeBehavior);
    }
    @Override
    public void onlineUpdate(GEdge edge) {
        edge.setLearnValue((1. - 0.1) * edge.getLearnValue() + 0.1 * getSpaceStructure().getInitLearnValue());
    }

    @Override
    public int selectNextNode()
    {
        if(selectNextNodeBehavior == null)
            selectNextNodeBehavior = new AntNSelectNextNode();
        return selectNextNodeBehavior.selectNextNode(this);
    }

    public int selectBestNextNode(List<GEdge> neighborEdgeList) {
        Random random = new Random();
        int nextNodeIndex;
        double nextNodePossibility = -1;
        List<Integer> equalPossibilityNode = new ArrayList<>();

        int neighborSize = neighborEdgeList.size();
        double[] p = new double[neighborSize];

        for (int i = 0; i < neighborSize; i++) {
            p[i] = proportionalPossibility(i, neighborEdgeList);
        }

        for (int i = 0; i < neighborSize; i++) {
            if (p[i] > nextNodePossibility) {
                nextNodePossibility = p[i];
                equalPossibilityNode = new ArrayList();
                equalPossibilityNode.add(i);
            } else if (p[i] == nextNodePossibility) {
                equalPossibilityNode.add(i);
            }
        }

        /*Baraye zamani ke chand node ba ehtemale barabar hastesh*/
        int rand = random.nextInt(equalPossibilityNode.size());

        nextNodeIndex = equalPossibilityNode.get(rand);

        return nextNodeIndex;
    }

    public double proportionalPossibility(int secondNodeIndex, List<GEdge> neighborEdgeList) {
//        Node currentNode = getSpaceStructure().getNode(getCurrentNodeIndex());
        Node secondNode = neighborEdgeList.get(secondNodeIndex).getSecondNode();
        double pi =secondNode.getSymbol().getSymbolWeight();
        double lambda = Math.pow((double)1 / (2 + pi), getNodePath().size());
        return neighborEdgeList.get(secondNodeIndex).getLearnValue() * Math.pow(lambda, getBeta());

    }

    public double getQZero() {
        return qZero;
    }


    public double getBeta() {
        return beta;
    }
}
