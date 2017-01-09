package Agent;

import Algorithm.HUD;
import Algorithm.HeuristicInformation;
import Behavior.SelectFirstNodeBehavior;
import Behavior.WDNSelectNextNode;
import Benchmark.Benchmark;
import Space.GEdge;
import Space.Node;
import Space.SpaceStructure;

import java.util.List;

public class WD extends Agent {

    public static double tra = 1.0, trb = 0.01, trc = 1.0;
    public static double at = 1, bt = .01, ct = 1;

    public double velocity;
    double initvelocity = 0;
    double soil;

    public WD(Agent agent) {
        super(agent);

        setVelocity(((WD)agent).getVelocity());
        setInitvelocity(((WD)agent).getInitvelocity());
        setSoil(((WD)agent).getSoil());
    }

    public WD(double minLearnValue, SpaceStructure spaceStructure, Benchmark formula, double initVelocity, SelectFirstNodeBehavior selectFirstNodeBehavior) {
        super(minLearnValue, spaceStructure, formula,selectFirstNodeBehavior);
        setInitvelocity(initVelocity);
        setVelocity(initVelocity);
        setSoil(0);
    }
    public WD(double minLearnValue, SpaceStructure spaceStructure, Benchmark formula, double initVelocity) {
        super(minLearnValue, spaceStructure, formula);
        setInitvelocity(initVelocity);
        setVelocity(initVelocity);
        setSoil(0);
    }

    @Override
    public void onlineUpdate(GEdge edge) {
        double dSoil;
        double edgeSoil = edge.getLearnValue();
        setVelocity(getVelocity() + tra / (trb + trc * (Math.pow(edgeSoil, 2))));
        dSoil = at / (bt + ct * Math.pow(calTime(getVelocity(),edge), 2.0));
        edge.setLearnValue((1 - ros) * edgeSoil - rosd * dSoil);
        updateDropSoil(dSoil);
    }

    public int selectNextNode()
    {
        if(selectNextNodeBehavior == null)
            selectNextNodeBehavior = new WDNSelectNextNode();
        return selectNextNodeBehavior.selectNextNode(this);
    }

    public double proportionalPossibility(int secondNodeIndex, List<GEdge> neighborEdgeList) {
        Node secondNode = neighborEdgeList.get(secondNodeIndex).getSecondNode();
        double pi =secondNode.getSymbol().getSymbolWeight();
        double sf= HeuristicInformation.getSF(secondNode.getSymbol().getPresentation());
        if(!benchmark.isUseSF())
            sf = 1;
        double lambda = Math.pow(sf/ (2 + pi), getNodePath().size());
//        lambda=1;
        return lambda*fSoil(secondNodeIndex, neighborEdgeList);
    }

    public double zigmaFSoil(List<GEdge> neighborEdgeList) {

        double zigmaFSoil = 0;
        //List<Space.GEdge> neighborEdgeList = spaceStructure.getNode(currentNodeIndex).getEdgesList();

        for (int i = 0; i < neighborEdgeList.size(); i++) {
            zigmaFSoil += fSoil(i, neighborEdgeList);
        }

        return zigmaFSoil;
    }

    public double fSoil(int secondNodeIndex, List<GEdge> neighborEdgeList) {

        //double minLearnValue = spaceStructure.getNode(currentNodeIndex).getEdge(minEdgeSoilIndex()).getLearnValue();
        double minSoil = neighborEdgeList.get(minEdgeSoilIndex(neighborEdgeList)).getLearnValue();
//        double minSoil = neighborEdgeList.stream().map(GEdge::getLearnValue).min(Double::compare).get();

        double gSoil = neighborEdgeList.get(secondNodeIndex).getLearnValue();
        if (minSoil < 0) {
            gSoil -= minSoil;
        }
      //  lambda =1.0;
        return (1.0 / (0.0001 + gSoil));
    }

    public int minEdgeSoilIndex(List<GEdge> neighborEdgeList) {

        double minSoil = Double.MAX_VALUE;
        int minIndex = 0;
        //List<Space.GEdge> neighborEdge = spaceStructure.getNode(currentNodeIndex).getEdgesList();

        for (int index = 0; index < neighborEdgeList.size(); index++) {
            if (neighborEdgeList.get(index).getLearnValue() < minSoil) {
                minSoil = neighborEdgeList.get(index).getLearnValue();
                minIndex = index;
            }
        }
        return minIndex;
    }

    public void updateDropSoil(double soil) {
        this.soil += soil;
    }

    public double calTime(double dVelocity , GEdge edge) {
        double hud = 1;
        if (dVelocity <= 0) dVelocity = .0001;//changed it from 1 to .0001 for now

        if(benchmark.isUseHUD()) {
            hud = HUD.getVal(edge.getFirstNode().getSymbol().getPresentation(),
                    edge.getSecondNode().getSymbol().getPresentation());
        }
        return (hud / dVelocity);
    }

    public void reInitial() {
        super.reInitial();

        setSoil(0);
        setVelocity(getInitvelocity() + random.nextDouble());
    }

    /****************Set and Get****************/

    public double getVelocity() {
        return velocity;
    }

    public double getInitvelocity() {
        return initvelocity;
    }

    public double getSoil() {
        return soil;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setInitvelocity(double initvelocity) {
        this.initvelocity = initvelocity;
    }

    public void setSoil(double soil) {
        this.soil = soil;
    }
}
