
package Algorithm;

import Agent.*;
import Benchmark.*;
import Benchmark.Formula.Formula;
import Benchmark.Formula.Koza1;
import Space.*;

public class MetaHeuristicController {

    private Benchmark benchmark = new NguyenBenchmark();
    private int countOfEachSymbol = 10;
    private double initLearnValue = 10000;

    private double minLearnValue = 0.01;
    private double initVelocity = 5;

    public MetaHeuristicController() {
        getBenchmark().setFormula(new Koza1());
        HUD.init(getBenchmark().getFormula());

        CompleteGraphMultiSymbol antCompleteGraphMultiSymbol = new CompleteGraphMultiSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        CompleteGraphMultiSymbol wdCompleteGraphMultiSymbol = new CompleteGraphMultiSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        CompleteGraphSingleSymbol antCompleteGraphSingleSymbol = new CompleteGraphSingleSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        CompleteGraphSingleSymbol wdCompleteGraphSingleSymbol = new CompleteGraphSingleSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        RandomGraphMultiSymbol antRandomGraphMultiSymbol = new RandomGraphMultiSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        RandomGraphMultiSymbol wdRandomGraphMultiSymbol = new RandomGraphMultiSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        RandomGraphSingleSymbol antRandomGraphSingleSymbol = new RandomGraphSingleSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        RandomGraphSingleSymbol wdRandomGraphSingleSymbol = new RandomGraphSingleSymbol(getInitLearnValue(), getFormula(), getCountOfEachSymbol());
        Tree antTree = new Tree(getInitLearnValue(), getFormula());
        Tree wdTree = new Tree(getInitLearnValue(), getFormula());
        System.out.println("ACGMS");
     //   AntP ACGMS = new AntP(antCompleteGraphMultiSymbol, antGraphMultiSymbolConstructor(antCompleteGraphMultiSymbol), getMinLearnValue());
//        System.out.println("ARGMS");
//        AntP ARGMS = new AntP(antRandomGraphMultiSymbol, antGraphMultiSymbolConstructor(antRandomGraphMultiSymbol), getMinLearnValue());
//        System.out.println("ACGSS");
//        AntP ACGSS = new AntP(antCompleteGraphSingleSymbol, antGraphSingleSymbolConstructor(antCompleteGraphSingleSymbol), getMinLearnValue());
//        System.out.println("ARGSS");
//        AntP ARGSS = new AntP(antRandomGraphSingleSymbol, antGraphSingleSymbolConstructor(antRandomGraphSingleSymbol), getMinLearnValue());
//        System.out.println("AT");
//        AntP AT = new AntP(antTree, antTreeConstructor(antTree), getMinLearnValue());

//        System.out.println("WDCGMS");
//        IWDP WDCGMS = new IWDP(wdCompleteGraphMultiSymbol, wdGraphMultiSymbolConstructor(wdCompleteGraphMultiSymbol), getMinLearnValue(),benchmark);
//        System.out.println("WDRGMS");
//        IWDP WDRGMS = new IWDP(wdRandomGraphMultiSymbol, wdGraphMultiSymbolConstructor(wdRandomGraphMultiSymbol), getMinLearnValue());
//        System.out.println("WDCGSS");
//        IWDP WDCGSS = new IWDP(wdCompleteGraphSingleSymbol, wdGraphSingleSymbolConstructor(wdCompleteGraphSingleSymbol), getMinLearnValue());
//        System.out.println("WDRGSS");
//        IWDP WDRGSS = new IWDP(wdRandomGraphSingleSymbol, wdGraphSingleSymbolConstructor(wdRandomGraphSingleSymbol), getMinLearnValue());
        System.out.println("WDT");
        IWDP WDT = new IWDP(wdTree, wdTreeConstructor(wdTree), getMinLearnValue(), benchmark);

        getBenchmark().printResults();
    }

    public static void main(String[] args) {
        MetaHeuristicController metaHeuristicController = new MetaHeuristicController();
    }

    public Agent[] antGraphMultiSymbolConstructor(Graph graph) {
        Ant[] agents = new Ant[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createAntGraph(getMinLearnValue(), graph, benchmark);
        }
        return agents;
    }

    public Agent[] antGraphSingleSymbolConstructor(Graph graph) {
        Ant[] agents = new Ant[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createAntGraph(getMinLearnValue(), graph, benchmark);
        }
        return agents;
    }

    public Agent[] antTreeConstructor(Tree tree) {
        Ant[] agents = new Ant[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createAntTree(getMinLearnValue(), tree,benchmark);
        }
        return agents;
    }

    public Agent[] wdGraphMultiSymbolConstructor(Graph graph) {
        WD[] agents = new WD[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createWDGraph(getMinLearnValue(), graph, benchmark, getInitVelocity());
        }
        return agents;
    }

    public Agent[] wdGraphSingleSymbolConstructor(Graph graph) {
        WD[] agents = new WD[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] =AgentBuilder.createWDGraph(getMinLearnValue(), graph,benchmark, getInitVelocity());
        }
        return agents;
    }

    public Agent[] wdTreeConstructor(Tree tree) {
        WD[] agents = new WD[getBenchmark().getAgentsN()];
        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createWDTree(getMinLearnValue(), tree, benchmark, getInitVelocity());
        }
        return agents;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public double getMinLearnValue() {
        return minLearnValue;
    }

    public Formula getFormula() {
        return benchmark.getFormula();
    }

    public double getInitVelocity() {
        return initVelocity;
    }

    public double getInitLearnValue() {
        return initLearnValue;
    }

    public int getCountOfEachSymbol() {
        return countOfEachSymbol;
    }
}

