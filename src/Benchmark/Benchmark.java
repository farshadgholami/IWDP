package Benchmark;

import Benchmark.Formula.Formula;

import java.util.ArrayList;

/**
 * Created by Pouya Payandeh on 2/28/2016.
 */
public abstract class Benchmark {
    //    public void newRun(){}
//    public void newIteration(){}
    Formula f;
    protected int runsN,iterationsN,agentsN;
    protected boolean useHUD,useSF;

    public boolean isUseHUD()
    {
        return useHUD;
    }

    public void setUseHUD(boolean useHUD)
    {
        this.useHUD = useHUD;
    }

    public boolean isUseSF()
    {
        return useSF;
    }

    public void setUseSF(boolean useSF)
    {
        this.useSF = useSF;
    }

    public abstract double getFitness(ArrayList<Double> values);
    public Formula getFormula() {
        return f;
    }
    public void setFormula(Formula f) {
        this.f = f;
    }
    public abstract void printResults();

    public int getRunsN() {
        return runsN;
    }

    public int getIterationsN() {
        return iterationsN;
    }

    public int getAgentsN() {
        return agentsN;
    }

    public void setRunsN(int runsN)
    {
        this.runsN = runsN;
    }

    public void setIterationsN(int iterationsN)
    {
        this.iterationsN = iterationsN;
    }

    public void setAgentsN(int agentsN)
    {
        this.agentsN = agentsN;
    }
}
