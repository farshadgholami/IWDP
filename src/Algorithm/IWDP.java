package Algorithm;

import Agent.*;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.SpaceStructure;

public class IWDP extends MetaHeuristic {

    public IWDP(SpaceStructure spaceStructure, Agent[] agents, double minLearnValue , Benchmark benchmark) {
        super(spaceStructure, agents, minLearnValue,benchmark);
    }

    @Override
    public void offlineUpdate(int bestAgentIndex) {
        double fitRate = 1;
        //fitRate = mydrops[bestDrop].fitness;

        double division = getAgent(bestAgentIndex).getNodePath().size() - 1;
        double dSoil = ((WD)getAgent(bestAgentIndex)).getSoil() / division;

        GEdge edge;
        for (int i = 0; i < getAgent(bestAgentIndex).getEdgePath().size(); i++) {
            edge = getAgent(bestAgentIndex).getEdgePath().get(i);
            edge.setLearnValue((1 - getRo()) * edge.getLearnValue() - fitRate * getRo() * dSoil);
            checkLeastOfLearnValue(edge);
        }
    }

    @Override
    public Agent[] cloneAgents(Agent[] myAgents) {
        Agent[] agents = new Agent[myAgents.length];

        for (int i = 0; i < myAgents.length; i++) {
            //CMA
            agents[i] = new WD(myAgents[i]);
        }

        return agents;
    }

    @Override
    public Agent cloneAgent(Agent agent) {
        //CMA
        return new WD(agent);
    }
}
