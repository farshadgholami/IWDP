package Behavior;

import Agent.Agent;
import Space.GEdge;
import Space.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class WDNSelectNextNode implements SelectNextNodeBehavior
{
    @Override
    public int selectNextNode(Agent agent)
    {
        Random random = new Random();
        int nextNode = 0;
        Node currentNode = agent.getSpaceStructure().getNode(agent.getCurrentNodeIndex());
        List<Node> neighborNodes = currentNode.getLinkedNodes();
        List<GEdge> neighborEdgeList = new ArrayList<>(currentNode.getEdgesList());
        int neighborSize = neighborEdgeList.size();

        double[] p = new double[neighborSize];
        double nextp = random.nextDouble();

        /////////////// b

        //Last Space.Node Neighbor Index
        int LNNI;
        for (LNNI = 0; LNNI < neighborSize; ++LNNI) {
            if (agent.getLastNodeIndex() == neighborNodes.get(LNNI).getIndexInGraph()) {
                neighborNodes.remove(LNNI);
                neighborEdgeList.remove(LNNI);
                neighborSize--;
                break;
            }
        }

        ////////////////// c

        for (int i = 0; i < neighborSize; i++) {
            p[i] = agent.proportionalPossibility(i, neighborEdgeList);
        }

        //az inJa be baado dare kare hamun zigmaF ba tarkibi az
        //roulletwell ro piade sazi mikone
        for (int i = 0; i < (neighborSize - 1); ++i)
            p[i + 1] += p[i];

        for (int i = 0; i < neighborSize; ++i) {
            p[i] /= p[neighborSize - 1];
        }

        for (int i = 0; i < neighborSize; ++i) {
            if (p[i] >= nextp) {
                //nextNode = i;
                nextNode = currentNode.getLinkedNodes().indexOf(neighborNodes.get(i));
                break;
            }
        }

        return nextNode;
    }
}
