package Behavior;

import Agent.Agent;
import Agent.Ant;
import Space.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class AntSelectNextNode implements SelectNextNodeBehavior
{
    public int selectNextNode(Agent agent) {
        assert agent instanceof Ant;
        Ant ant = (Ant) agent;
        int nextNode;
        Random random = new Random();
        List<GEdge> neighborEdgeList = null;
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        if(agent.getSpaceStructure() instanceof CompleteGraphMultiSymbol)
            neighborEdgeList = agent.neighborEdgeList_GraphMultiSymbol_Creator();
        else if(agent.getSpaceStructure() instanceof CompleteGraphSingleSymbol)
            neighborEdgeList = agent.neighborEdgeList_GraphSingleSymbol_Creator();
        else if(agent.getSpaceStructure() instanceof Tree)
            neighborEdgeList = new ArrayList<>(currentNode.getEdgesList());

        assert  neighborEdgeList != null;
        assert  neighborEdgeList.size() != 0 ;


        double q = random.nextDouble();
        if (ant.getQZero() > 0.0 && q < ant.getQZero()) {
        /* we first check whether q_0 > 0.0, to avoid the very common case of q_0 = 0.0 to have to compute a random number, which is expensive computationally */
            return ant.selectBestNextNode(neighborEdgeList);
        }

        nextNode = ant.rouletteWheel(neighborEdgeList);

        /* This may very rarely happen because of rounding if rnd is close to 1.*/
        if (nextNode == neighborEdgeList.size()) {
            return ant.selectBestNextNode(neighborEdgeList);
        }

        return nextNode;
    }
}
