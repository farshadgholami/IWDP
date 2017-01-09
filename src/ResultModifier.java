import Benchmark.Formula.Formula;
import Benchmark.Formula.Nguyen.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by farsh on 12/17/2016.
 */
public class ResultModifier {


//    static Formula formulas[] = {new F1(), new F2(), new F3(), new F4(), new F5(), new F6(), new F7(), new F8(), new F9(), new F10()};
    static Formula formulas[] = {new F5(), new F6()};
    static ArrayList<ArrayList<ArrayList<ArrayList<ResultFileParser>>>> confsResult = new ArrayList<>();
    static ArrayList<ResultFileParser> ap_result = new ArrayList<>();

    static int agentSweep[] = {100, 200, 500, 1000};
    static int iterSweep[] = {50, 100, 150, 300};

    public static void main(String[] args) throws IOException {
        int ITERATION_NUM = 100;
        int AGENTS_NUM = 500;

        boolean confs[][] = {{false, false}, {true, false}, {false, true}, {true, true}};

        BufferedReader br;
        ResultFileParser rfp;

        ArrayList<ArrayList<ArrayList<ResultFileParser>>> formulaResult;
        ArrayList<ArrayList<ResultFileParser>> resultHolder;
        ArrayList<ResultFileParser> agentSweepResult;
        ArrayList<ResultFileParser> iterationSweepResult;

        for (int i = 0; i < 4; i++) {
            formulaResult = new ArrayList<>();

            for (int f = 0; f < formulas.length; f++) {
                resultHolder = new ArrayList<>();
                agentSweepResult = new ArrayList<>();
                iterationSweepResult = new ArrayList<>();

                if (i == 0) {
                    for (int s = 0; s < agentSweep.length; s++) {
                        String filePath = "LOG\\"
                                + String.format("Sweep_%s_%s_%s_", i, agentSweep[s], ITERATION_NUM)
                                + formulas[f].getName()
                                + ".log";
                        if (!(new File(filePath).exists())) {
                            System.out.println(filePath + " --> not Exist");
                            continue;
                        }

                        br = new BufferedReader(new FileReader(filePath));
                        rfp = new ResultFileParser(br);
                        br.close();

                        agentSweepResult.add(rfp);
                    }

                    for (int s = 0; s < iterSweep.length; s++) {
                        String filePath = String.format("LOG\\Sweep_%s_%s_%s_%s.log", i, AGENTS_NUM, iterSweep[s],
                                formulas[f].getName());
                        if (!(new File(filePath).exists())) {
                            System.out.println(filePath + " --> not Exist");
                            continue;
                        }

                        br = new BufferedReader(new FileReader(filePath));
                        rfp = new ResultFileParser(br);
                        br.close();

                        iterationSweepResult.add(rfp);
                    }

                } else {
                    String filePath = "LOG\\"
                            + String.format("Heuristic_%s_", i)
                            + formulas[f].getName()
                            + ".log";

                    if (!(new File(filePath).exists())) {
                        System.out.println(filePath + " --> not Exist");
                    }
                    br = new BufferedReader(new FileReader(filePath));
                    rfp = new ResultFileParser(br);
                    br.close();

                    iterationSweepResult.add(rfp);
                }

                resultHolder.add(iterationSweepResult);
                if (i == 0) {
                    resultHolder.add(agentSweepResult);
                }

                formulaResult.add(resultHolder);
            }

            confsResult.add(formulaResult);
        }

        for (int i = 0; i < formulas.length; i++) {
            br = new BufferedReader(new FileReader(String.format("LOG\\AP_RES_0_%s.log" , formulas[i].getName())));
            rfp = new ResultFileParser(br);
            br.close();

            ap_result.add(rfp);
        }

        originalCompare();
        sweepCompare("Agent", agentSweep.length, 1);
        sweepCompare("Iteration", iterSweep.length, 0);
        heuristicCompare();
    }

    public static void originalCompare() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Chart\\OriginalCompare.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.println("MeanBest:");
        writer.println("IWDP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(getResult(0, f, 0, 1).meanBest + " ");
        }
        writer.println();
        writer.println("AP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(ap_result.get(f).meanBest + " ");
        }

        writer.println();
        writer.println("SuccessfulRun");
        writer.println("IWDP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(getResult(0, f, 0, 1).successfulRuns + " ");
        }
        writer.println();
        writer.println("AP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(ap_result.get(f).successfulRuns + " ");
        }

        writer.println();
        writer.println("Min");
        writer.println("IWDP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(getResult(0, f, 0, 1).min + " ");
        }
        writer.println();
        writer.println("AP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(ap_result.get(f).min + " ");
        }

        writer.println();
        writer.println("AdjustedAvg");
        writer.println("IWDP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(getResult(0, f, 0, 1).adjustedAvg + " ");
        }
        writer.println();
        writer.println("AP:");
        for (int f = 0; f < formulas.length; f++) {
            writer.print(ap_result.get(f).adjustedAvg + " ");
        }

        writer.flush();
    }

    /**
     * Baraye Print e natayeje sweep dar har do noe Agent va Iteration
     *
     * @param name   Agent ya iteration
     * @param length Toole halatHaye sweep
     * @param type   0 baraye iteration 1 baraye agent
     */
    public static void sweepCompare(String name, int length, int type) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Chart\\" + name + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.println("MeanBest:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < length; j++) {
                writer.print(getResult(0, f, type, j).meanBest + " ");
            }
            writer.println();
        }
        writer.println("\nSuccessfulRun:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < length; j++) {
                writer.print(getResult(0, f, type, j).successfulRuns + " ");
            }
            writer.println();
        }
        writer.println("\nMin:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < length; j++) {
                writer.print(getResult(0, f, type, j).min + " ");
            }
            writer.println();
        }
        writer.println("\nAdjustedAvg:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < length; j++) {
                writer.print(getResult(0, f, type, j).adjustedAvg + " ");
            }
            writer.println();
        }

        writer.flush();
    }

    public static void heuristicCompare() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("Chart\\Heuristics.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.println("MeanBest:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                    writer.print(getResult(j, f, 0, 1).meanBest + " ");
                else
                    writer.print(getResult(j, f, 0, 0).meanBest + " ");
            }
            writer.println();
        }

        writer.println("\nSuccessfulRun:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                    writer.print(getResult(j, f, 0, 1).successfulRuns + " ");
                else
                    writer.print(getResult(j, f, 0, 0).successfulRuns + " ");
            }
            writer.println();
        }
        writer.println("\nMin:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                    writer.print(getResult(j, f, 0, 1).min + " ");
                else
                    writer.print(getResult(j, f, 0, 0).min + " ");
            }
            writer.println();
        }
        writer.println("\nAdjustedAvg:");
        for (int f = 0; f < formulas.length; f++) {
            writer.println(formulas[f].getName());
            for (int j = 0; j < 4; j++) {
                if (j == 0)
                    writer.print(getResult(j, f, 0, 1).adjustedAvg + " ");
                else
                    writer.print(getResult(j, f, 0, 0).adjustedAvg + " ");
            }
            writer.println();
        }

        writer.flush();
    }

    public static ResultFileParser getResult(int i, int j, int k, int w) {
        return confsResult.get(i).get(j).get(k).get(w);
    }
}
