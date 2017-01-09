package Behavior;

import Agent.Agent;
import Space.Graph;
import Space.Node;

import java.util.Random;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class SelectFirstNode implements SelectFirstNodeBehavior
{
    @Override
    public void selectFirstNode(Agent agent)
    {
        Random random= new Random();
        int BeginNodeIndex;
        Node firstNode;
        do {
            BeginNodeIndex = (int) (((Graph)agent.getSpaceStructure()).getNumberOfNodes() * random.nextDouble());
        } while (agent.getSpaceStructure().getNode(BeginNodeIndex).getSymbol().isTerminal());

        firstNode = agent.getSpaceStructure().getNode(BeginNodeIndex);
        agent.addToPath(firstNode);
    }
}
