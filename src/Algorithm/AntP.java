package Algorithm;

import Agent.Agent;
import Agent.Ant;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.SpaceStructure;

public class AntP extends MetaHeuristic {
    public AntP(SpaceStructure spaceStructure, Agent[] agents, double minLearnValue,Benchmark benchmark ) {
        super(spaceStructure, agents, minLearnValue,benchmark);
    }

    public void evaporation() {
        for (GEdge edge : getSpaceStructure().getEdges()) {
            edge.setLearnValue((1 - getRo()) * edge.getLearnValue());
            checkLeastOfLearnValue(edge);
        }
    }

    public void globalUpdatePheromone(int bestAntIndex) {
        double dTau;

        //dTau = 1.0 / (double) a.tour_length;
        dTau = 1.0 / getAgent(bestAntIndex).getFitness();

        for (GEdge edge : getAgent(bestAntIndex).getEdgePath()) {
            edge.setLearnValue(edge.getLearnValue() + dTau);
        }
    }

    public void globalAcsPheromoneUpdate(int bestAntIndex) {
        double dTau;

        //dTau = 1.0 / (double) a.tour_length;
        dTau = 1.0 / getAgent(bestAntIndex).getFitness();

        for (GEdge edge : getAgent(bestAntIndex).getEdgePath()) {
            edge.setLearnValue((1. - getRo()) * edge.getLearnValue() + getRo() * dTau);
            checkLeastOfLearnValue(edge);
        }
    }


    @Override
    public void offlineUpdate(int bestAgentIndex) {
        globalAcsPheromoneUpdate(bestAgentIndex);
    }

    @Override
    public Agent[] cloneAgents(Agent[] myAgents) {
        Agent[] agents = new Agent[myAgents.length];

        for (int i = 0; i < myAgents.length; i++) {
            //CMA
            agents[i] = new Ant(myAgents[i]);
        }

        return agents;
    }

    @Override
    public Agent cloneAgent(Agent agent) {
        //CMA
        return new Ant(agent);
    }

    /*
    @Override
    public void setBestGlobalAgentAllRunByTrain(Agent bestGlobalAgentAllRunByTrain) {
        super.bestGlobalAgentAllRunByTrain = new Ant(bestGlobalAgentAllRunByTrain);
    }

    @Override
    public void setBestGlobalAgentAllRunByTest(Agent bestGlobbalAgentAllRunByTest) {
        this.bestGlobalAgentAllRunByTest = new Ant(bestGlobalAgentAllRunByTest);
    }

    @Override
    public void setBestAgent(Agent bestAgent) {
        this.bestAgent = new Ant(bestAgent);
    }

    @Override
    public void setAgents(Agent[] myAgents) {
        for (int i = 0; i < myAgents.length; i++) {
            this.agents[i] = new Ant(myAgents[i]);
        }
    }*/
}
