package Algorithm;

import Agent.Agent;
import Benchmark.Formula.Formula;
import Space.GEdge;
import Symbol.GSymbol;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pouya Payandeh on 7/16/2016.
 */
public class HUD
{
    private static Map<String, Integer> map;
    private static int goods[][];
    private static int bads[][];
    private static int goodNum = 5;
    private static int badNum = 5;

    public static void init(Formula formula)
    {
        map = new TreeMap<>();
        GSymbol[] symbols = formula.getNonTerminalSet();
        int i = 0;
        map.put(new GSymbol().getPresentation(), i);
        i++;

        for (GSymbol g : symbols)
        {
            map.put(g.getPresentation(), i);
            i++;
        }
        symbols = formula.constantSet;
        if (symbols != null)
        {
            for (GSymbol g : symbols)
            {
                map.put(g.getPresentation(), i);
                i++;
            }
        }
        for (int k = 1; k <= formula.getVars(); k++)
        {
            map.put("x" + k, i);
            i++;
        }
        goods = new int[i][i];
        bads = new int[i][i];
    }

    public static void updateHUD(ArrayList<Integer> idxs, Agent[] agents)
    {
        calculate(idxs, agents, goodNum, goods);
        calculate(idxs, agents, badNum, bads);
    }

    public static void calculate(ArrayList<Integer> idxs, Agent[] agents, int bound, int T[][])
    {
        for (int i = 0; i < Math.min(bound, idxs.size()); i++)
        {
            for (GEdge e : agents[idxs.get(i)].getEdgePath())
            {
                String second = e.getSecondNode().getSymbol().getPresentation();
                String first = e.getFirstNode().getSymbol().getPresentation();
                updateTable(first, second, T);
            }
        }
    }

    static void updateTable(String first, String second, int T[][])
    {
        int i = map.get(first);
        int j = map.get(second);
        T[i][j]++;
    }

    public static double getVal(String first, String second)
    {
//        return 1.0;

        int i = map.get(first);
        int j = map.get(second);
        return (double) (bads[i][j] + 1) / (double) (goods[i][j] + 1);

    }
}
