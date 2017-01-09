import Agent.AgentBuilder;
import Agent.Ant;
import Agent.WD;
import Algorithm.AntP;
import Algorithm.HUD;
import Algorithm.IWDP;
import Algorithm.MetaHeuristic;
import Benchmark.Formula.Formula;
import Benchmark.Formula.Nguyen.*;
import Benchmark.NguyenBenchmark;
import Run.Arg;
import Run.ParameterGenerator;
import Run.Result;
import Space.Tree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Pouya Payandeh on 11/28/2016.
 */
public class BatchRun {

    private static int RANDOM_PARAMETER_PASSES= 10;
    private static int AGENTS_NUM = 500;
    private static int ITERATION_NUM =100;
    private static int RUNS_NUM = 100;
//    RANDOM_PARAMETER_PASSES= 5;
//    AGENTS_NUM = 500;
//    ITERATION_NUM =100;
//    RUNS_NUM = 100;

//    static Formula formulas[] = {new F1(), new F2(), new F3(), new F4(), new F5(), new F6(), new F7(), new F8(), new F9(), new F10()};
    private static Formula formulas[] = {new F5(), new F6()};
    private static boolean confs[][] ={{false,false},{true,false},{false,true},{true,true}};
    private static String logDir;
    private static Result bestResultsIWDP[] = new Result[formulas.length];

    public static void main(String[] args) {
        System.out.println("INITIALIZING");
        logDir = init(RANDOM_PARAMETER_PASSES, AGENTS_NUM, ITERATION_NUM, RUNS_NUM)+"\\";

        compeleteRun();
//        specificParamRun();
    }

    private static void compeleteRun() {

        for (int i = 0; i < 4; i++) {
            if (i != 0) {
                Result[] best = randomSearch(i);
                Arg args[] = new Arg[best.length];
                for (int j = 0; j < best.length; j++) {
                    args[j] = best[j].getArg();
                }
                sweepParameter(args, i);
            } else {
                specificParamRun(i);
            }
        }
    }

    private static void specificParamRun(int heuristicIndex) {
        Arg args[] = new Arg[formulas.length];

        for (int f = 0; f < formulas.length; f++) {
            try {
                String file = "LOG\\" + String.format("IWDP_RES_%s_", heuristicIndex)
                        + formulas[f].getName()
                        + ".log";
//                BufferedReader br = new BufferedReader(new FileReader(String.format("LOG\\%d", f + 1)));
                BufferedReader br = new BufferedReader(new FileReader(file));
                ResultFileParser rfp = new ResultFileParser(br);
                args[f] = new Arg();
                args[f].at = rfp.at;
                args[f].bt = rfp.bt;
                args[f].ct = rfp.ct;
                args[f].initl = rfp.initl;
                args[f].initv = rfp.initv;
                args[f].rei = rfp.rei;
                args[f].ro = rfp.ro;
                args[f].ros = rfp.ros;
                args[f].rosd = rfp.rosd;
                args[f].tra = rfp.tra;
                args[f].trb = rfp.trb;
                args[f].trc = rfp.trc;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        sweepParameter(args, heuristicIndex);

//        for (int i = 0; i < 4; i++) {
//            sweepParameter(args, i);
//        }

    }

    private static Result[] randomSearch(int i) {
        System.out.println("Starting Random Parameter Search");

        System.out.printf("Starting Randomize Parameter for Sf=%s,hud=%s\n",confs[i][1],confs[i][0]);
        for(int f = 0 ; f < formulas.length ; f++)
        {
            System.out.println("Selected Formula = " + formulas[f].getName());
            ArrayList<Result> resultsIWDP= new ArrayList<>();
            ArrayList<Result> resultsAP= new ArrayList<>();
            for (int j = 0; j < RANDOM_PARAMETER_PASSES; j++) {
                System.out.printf("\r Pass %d of %d", j, RANDOM_PARAMETER_PASSES);
                Arg arg = null;
                try {
                    arg = ParameterGenerator.generate(Arg.class);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                NguyenBenchmark benchmark = getNguyenBenchmark(AGENTS_NUM, ITERATION_NUM, RUNS_NUM, formulas[f], confs[i]);
                resultsIWDP.add(runOnceIWDP(arg, benchmark, "No Name", true));

                benchmark = getNguyenBenchmark(AGENTS_NUM, ITERATION_NUM, RUNS_NUM, formulas[f], confs[i]);
                resultsAP.add(runOnceAP(arg, benchmark, "No Name", true));
            }
            System.out.println();
            String fileIWDP =  logDir + String.format("IWDP_RES_%s_", i)
                    + formulas[f].getName()
                    + ".log";

            String fileAP =  logDir + String.format("AP_RES_%s_", i)
                    + formulas[f].getName()
                    + ".log";
            try (PrintWriter writer = new PrintWriter(fileIWDP)) {
                resultsIWDP.sort((o1, o2) -> Double.compare(o1.meanBest, o2.meanBest));
//                resultsIWDP.forEach(writer::println);
                writer.println(resultsIWDP.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter writer = new PrintWriter(fileAP)) {
//                writer.println("+=====================================================+");
                resultsAP.sort((o1, o2) -> Double.compare(o1.meanBest, o2.meanBest));
//                resultsAP.forEach(writer::println);
                writer.println(resultsAP.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            bestResultsIWDP[f] = resultsIWDP.get(0);
//            bestResultsAP[f] = resultsAP.get(0);
        }

        return bestResultsIWDP;
    }

    /**
     * Age heuristicIndex == 0 bashe, miad ba hameye agentSweep va iterSweep ha ba parameterHaye arg e voroodi
     * algorithm ro baraye hameye formula run mikone.
     * age heuristicIndex > 0, faghat mire ba tedad AGENTS_NUM va ITERATION_NUM moshakhas shode, algorithm ro ba
     * heuristic shomareye heuristicIndex run mikone
     * @param arg Run argumants
     * @param heuristicIndex Heuristic Index
     */
    private static void sweepParameter(Arg[] arg, int heuristicIndex) {

        int agentSweep[] = {100, 200, 500, 1000};
        int iterSweep[] = {50, 100, 150, 300};

        System.out.println("Starting Parameter Sweep");

        System.out.printf("Starting Randomize Parameter for Sf=%s,hud=%s\n", confs[heuristicIndex][1], confs[heuristicIndex][0]);
        for (int f = 0; f < formulas.length; f++) {
            System.out.println("Selected Formula = " + formulas[f].getName());
            if (heuristicIndex == 0) {
                for (int s = 0; s < agentSweep.length; s++) {
                    NguyenBenchmark benchmark = getNguyenBenchmark(agentSweep[s], ITERATION_NUM, RUNS_NUM, formulas[f], confs[heuristicIndex]);

                    String fileName = String.format("Sweep_%s_%s_%s_", heuristicIndex, agentSweep[s], ITERATION_NUM)
                            + benchmark.getFormula().getName()
                            + ".log";
//                    if (new File("LOG\\" + fileName).exists()) {
//                        continue;
//                    }
                    if (new File(logDir + fileName).exists()) {
                        continue;
                    }
                    System.out.println("Agent Size  = " + agentSweep[s]);
                    runOnceIWDP(arg[f], benchmark, logDir + fileName, false);
                }

                for (int s = 0; s < iterSweep.length; s++) {
                    NguyenBenchmark benchmark = getNguyenBenchmark(AGENTS_NUM, iterSweep[s], RUNS_NUM, formulas[f], confs[heuristicIndex]);

                    String fileName = String.format("Sweep_%s_%s_%s_", heuristicIndex, AGENTS_NUM, iterSweep[s])
                            + benchmark.getFormula().getName()
                            + ".log";
//                    if (new File("LOG\\" + fileName).exists()) {
//                        continue;
//                    }
                    if (new File(logDir + fileName).exists()) {
                        continue;
                    }
                    System.out.println("Iteration Size  = " + iterSweep[s]);
                    runOnceIWDP(arg[f], benchmark, logDir + fileName, false);
                }
            } else {
                NguyenBenchmark benchmark = getNguyenBenchmark(AGENTS_NUM, ITERATION_NUM, RUNS_NUM, formulas[f], confs[heuristicIndex]);

                String fileName = String.format("Heuristic_%s_", heuristicIndex)
                        + benchmark.getFormula().getName()
                        + ".log";
//                if (new File("LOG\\" + fileName).exists()) {
//                    continue;
//                }
                if (new File(logDir + fileName).exists()) {
                    continue;
                }
                runOnceIWDP(arg[f], benchmark, logDir + fileName, false);
            }
        }
    }

    private static NguyenBenchmark getNguyenBenchmark(int AGENTS_NUM, int ITERATION_NUM, int RUNS_NUM, Formula formula, boolean[] conf) {
        NguyenBenchmark benchmark = new NguyenBenchmark();
        benchmark.setFormula(formula);
        benchmark.setAgentsN(AGENTS_NUM);
        benchmark.setRunsN(RUNS_NUM);
        benchmark.setIterationsN(ITERATION_NUM);
        benchmark.setUseHUD(conf[0]);
        benchmark.setUseSF(conf[1]);
        return benchmark;
    }

    private static String init(int RPP, int AN, int IN, int RN) {
        String file = String.format("RandParam-%d AgN-%d IterN-%d RN-%d", RPP, AN, IN, RN) +
                LocalDateTime.now().toString().replaceAll(":"," ")+" logs";
        try
        {
            Files.createDirectory(Paths.get(file));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    private static Result runOnceIWDP(Arg arg, NguyenBenchmark benchmark, String fileName, boolean dump) {
        if(dump)
            fileName="dump";

        try(PrintWriter writer = new PrintWriter(fileName)) {
            MetaHeuristic.ro = arg.ro;
            MetaHeuristic.ros = arg.ros;
            MetaHeuristic.rosd = arg.rosd;
            MetaHeuristic.reInitialItrNo = arg.rei;

            if (benchmark.isUseHUD())
                HUD.init(benchmark.getFormula());

            Tree tree = new Tree(arg.initv, benchmark.getFormula());

            arg.setWD();
            WD[] agentsW = new WD[benchmark.getAgentsN()];
            for (int i = 0; i < agentsW.length; i++)
                agentsW[i] = AgentBuilder.createWDTree(arg.initl, tree, benchmark, arg.initv);

            IWDP WDT = new IWDP(tree, agentsW, arg.initl, benchmark);
            Result result = new Result(arg, benchmark.getBest(), benchmark.getWorst(), benchmark.getAverage(),
                    benchmark.getAdjustedAverage(), benchmark.getMeanBest(), WDT.getBestPath(), 0);
            result.setSuccessfulRuns(benchmark.getSuccessfulRun());
            writer.println(result);
            writer.flush();
            return result;
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static Result runOnceAP(Arg arg, NguyenBenchmark benchmark, String fileName, boolean dump) {
        if(dump)
            fileName="dump";

        try(PrintWriter writer = new PrintWriter(fileName)) {
            MetaHeuristic.ro = arg.ro;
            MetaHeuristic.ros = arg.ros;
            MetaHeuristic.rosd = arg.rosd;
            MetaHeuristic.reInitialItrNo = arg.rei;

            if (benchmark.isUseHUD())
                HUD.init(benchmark.getFormula());

            Tree tree = new Tree(arg.initv, benchmark.getFormula());

            Ant[] agentsA = new Ant[benchmark.getAgentsN()];
            for (int i = 0; i < agentsA.length; i++)
                agentsA[i] = AgentBuilder.createAntTree(arg.initl, tree, benchmark);

            AntP AT = new AntP(tree, agentsA, arg.initl, benchmark);
            Result result = new Result(arg, benchmark.getBest(), benchmark.getWorst(), benchmark.getAverage(),
                    benchmark.getAdjustedAverage(), benchmark.getMeanBest(), AT.getBestPath(), 0);
            result.setSuccessfulRuns(benchmark.getSuccessfulRun());
            writer.println(result);
            writer.flush();
            return result;
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
