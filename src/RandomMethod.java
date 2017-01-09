import Agent.*;
import Algorithm.HUD;
import Algorithm.IWDP;
import Algorithm.MetaHeuristic;
import Benchmark.*;
import Benchmark.Formula.*;
import Run.Arg;
import Run.ParameterGenerator;
import Run.Result;
import Space.Tree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Created by Pouya Payandeh on 2/26/2016.
 */


public class RandomMethod {

    static int countOfEachSymbol = 10;
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, FileNotFoundException
    {
        ArrayList<Result> results= new ArrayList<>();
        //-initv 3 -rei 5 -initl 1000 -ro 0.1 -ros 0.1 -rosd 0.1 -trc 0.1 -ct 0.1 -trb 1 -at 1
        String file =LocalDateTime.now().toString().replaceAll(":"," ")+".log";
        file = "WD" + file;
        PrintWriter writer = new PrintWriter(file);
        for(int run = 0;  run < 100 ; run++)
        {

            Arg arg = ParameterGenerator.generate(Arg.class);
             NguyenBenchmark benchmark= new NguyenBenchmark();
            arg.setWD();
           // ROUXBenchmark benchmark = new ROUXBenchmark();
            benchmark.setFormula(new Nguyen7());
            HUD.init(benchmark.getFormula());
//        CompleteGraphMultiSymbol antCompleteGraphMultiSymbol =
//                new CompleteGraphMultiSymbol(arg.initv, benchmark.getF(), getCountOfEachSymbol());
            Tree tree = new Tree(arg.initv, benchmark.getFormula());
            WD[] agents = new WD[benchmark.getAgentsN()];
//            Ant[] agents = new Ant[benchmark.getAgentsN()];
            for (int i = 0; i < agents.length; i++)
            {
                agents[i] = AgentBuilder.createWDTree(arg.initl, tree, benchmark, arg.initv);
//                agents[i] = AgentBuilder.createAntTree(arg.minLearnValue, tree, benchmark);
            }
            MetaHeuristic.ro = arg.ro;
            MetaHeuristic.ros = arg.ros;
            MetaHeuristic.rosd = arg.rosd;
            MetaHeuristic.reInitialItrNo = arg.rei;
            IWDP WDT = new IWDP(tree, agents, arg.initl, benchmark);
//            AntP ANT = new AntP(tree, agents, arg.minLearnValue, benchmark);
            Result result = new Result(arg,benchmark.getBest(),benchmark.getWorst(),benchmark.getAverage(),
                   benchmark.getAdjustedAverage(), benchmark.getMeanBest(), WDT.getBestPath(),run);
            result.setSuccessfulRuns(benchmark.getSuccessfulRun());
            results.add(result);System.out.println(run);
            writer.println(result);
            writer.flush();
        }
        writer.println("+======================================================+");
        results.sort((o1, o2) -> Double.compare(o1.max,o2.max));
        results.forEach(writer::println);

        double mean = results.stream().map(result -> result.max).reduce((s, s2) -> s+s2).get() / results.size();
        System.out.println(mean);
        writer.println(mean);
        writer.close();
    }

}
