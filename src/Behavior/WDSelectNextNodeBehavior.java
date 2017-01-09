package Behavior;

import Agent.Agent;
import Space.*;

import java.util.List;
import java.util.List;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class WDSelectNextNodeBehavior implements SelectNextNodeBehavior
{
    @Override
    public int selectNextNode(Agent agent)
    {
        int nextNode;
        List<GEdge> neighborEdgeList = null;
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        if(agent.getSpaceStructure() instanceof CompleteGraphMultiSymbol)
            neighborEdgeList = agent.neighborEdgeList_GraphMultiSymbol_Creator();
        else if(agent.getSpaceStructure() instanceof CompleteGraphSingleSymbol)
            neighborEdgeList = agent.neighborEdgeList_GraphSingleSymbol_Creator();
        else if(agent.getSpaceStructure() instanceof Tree)
            neighborEdgeList = (currentNode.getEdgesList());

        nextNode = agent.rouletteWheel( neighborEdgeList);
        return nextNode;
    }
}
