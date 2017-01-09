package Behavior;

import Agent.Agent;
import Space.Node;
import Space.Tree;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class TreeMove extends GraphMove
{
    @Override
    public void move(Agent agent)
    {
        super.move(agent);
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        if (currentNode.getEdgesList().size() == 0) {
            ((Tree)agent.getSpaceStructure()).addNewLevel(currentNode);
        }
    }
}
