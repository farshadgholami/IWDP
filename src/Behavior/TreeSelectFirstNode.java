package Behavior;

import Agent.Agent;
import Space.Node;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class TreeSelectFirstNode implements SelectFirstNodeBehavior
{
    @Override
    public void selectFirstNode(Agent agent)
    {
        int BeginNodeIndex = 0;
        Node firstNode;
        firstNode = agent.getSpaceStructure().getNode(BeginNodeIndex);
        agent.addToPath(firstNode);
    }
}
