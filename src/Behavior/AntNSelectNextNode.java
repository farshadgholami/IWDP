package Behavior;

import Agent.Agent;
import Agent.Ant;
import Space.GEdge;
import Space.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class AntNSelectNextNode implements SelectNextNodeBehavior
{
    public int selectNextNode(Agent agent) {
        assert agent instanceof Ant;
        Ant ant = (Ant) agent;
        int nextNode = 0;
        Random random = new Random();
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        List<Node> neighborNodes = currentNode.getLinkedNodes();
        List<GEdge> neighborEdgeList = new ArrayList<>(currentNode.getEdgesList());
        int neighborSize = neighborEdgeList.size();

        double[] p = new double[neighborSize];
        double nextP = random.nextDouble();

        //////////////////// k

        double q = random.nextDouble();

        if (ant.getQZero() > 0.0 && q < ant.getQZero()) {
        /* we first check whether q_0 > 0.0, to avoid the very common case
	     * of q_0 = 0.0 to have to compute a random number, which is
	     * expensive computationally */
            return ant.selectBestNextNode(neighborEdgeList);
        }

        ///////////////////// c

        for (int i = 0; i < neighborSize; i++) {
            p[i] = agent.proportionalPossibility(i, neighborEdgeList);
        }

        for (int i = 0; i < (neighborSize - 1); ++i)
            p[i + 1] += p[i];

        for (int i = 0; i < neighborSize; ++i) {
            p[i] /= p[neighborSize - 1];
        }

        for (int i = 0; i < neighborSize; ++i) {
            if (p[i] >= nextP) {
                nextNode = i;
                break;
            }
        }
	    /*
	     * This may very rarely happen because of rounding if rnd is
	     * close to 1.
	     */

        ////////////////// e

        if (nextNode == neighborSize) {
            return ant.selectBestNextNode(neighborEdgeList);
        }

        return nextNode;
    }

}
