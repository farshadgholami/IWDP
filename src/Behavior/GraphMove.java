package Behavior;

import Agent.Agent;
import Space.GEdge;
import Space.Node;
import Space.Tree;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class GraphMove implements AgentMoveBehavior
{
    public void move(Agent agent) {
        int SecondNodeIndex;
        SecondNodeIndex = agent.selectNextNode();
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        GEdge edge = currentNode.getEdge(SecondNodeIndex);
        Node secondNode = edge.getSecondNode();
        agent.onlineUpdate(edge);
        agent.addToPath(secondNode, edge);
        agent.checkLeastOfLearnValue(edge);
    }
}
