package Agent;

import Behavior.*;
import Benchmark.Benchmark;
import Space.Graph;
import Space.Tree;

/**
 * Created by Pouya Payandeh on 2/5/2016.
 */
public class AgentBuilder
{
    public static Ant createAntGraph(double minLearnValue, Graph graph, Benchmark benchmark)
    {
        Ant ant = new Ant( minLearnValue,  graph, benchmark, new AntSelectNextNode());
        return ant;
    }
    public static Ant createAntTree(double minLearnValue, Tree tree,Benchmark benchmark)
    {
        Ant ant = new  Ant( minLearnValue,  tree, benchmark, new AntSelectNextNode(),new TreeSelectFirstNode());
        ant.setMoveBehavior(new TreeMove());
        return ant;
    }
    public static WD createWDGraph(double minLearnValue, Graph graph, Benchmark benchmark, double initVelocity)
    {
        WD wd = new WD( minLearnValue,  graph, benchmark,  initVelocity);
        wd.setSelectNextNodeBehavior(new WDSelectNextNodeBehavior());
        return wd;
    }
    public static WD createWDTree(double minLearnValue, Tree tree, Benchmark benchmark, double initVelocity)
    {
        WD wd =    new WD( minLearnValue,  tree, benchmark,  initVelocity,new TreeSelectFirstNode());
        wd.setSelectNextNodeBehavior(new WDSelectNextNodeBehavior());
        wd.setMoveBehavior(new TreeMove());
        return wd;
    }
}
