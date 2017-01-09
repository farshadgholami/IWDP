package Algorithm;

import Agent.Agent;
import Space.GEdge;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pouya Payandeh on 4/1/2016.
 */
public class HeuristicInformation {
    static Map<String, Double> avgs = new TreeMap<>();
    static int size = 4;

    public static void updateSF(ArrayList<Integer> idxs, Agent[] agents) {
        for (int i = 0; i < size; i++) {
            for (GEdge e : agents[idxs.get(i)].getEdgePath()) {
                String s = e.getSecondNode().getSymbol().getPresentation();
                if (avgs.containsKey(s))
                    avgs.put(s, (avgs.get(s) + 1.0) / 2.0);
                else
                    avgs.put(s, 1.0);
            }
        }
    }

    public static double getSF(String presentation) {
        //return 1;

        Double d = avgs.get(presentation);
        if (d == null)
            return 0.5;
        else
            return d;
    }
}
