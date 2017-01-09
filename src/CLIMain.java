import Agent.*;
import Algorithm.HUD;
import Algorithm.IWDP;
import Algorithm.MetaHeuristic;
import Benchmark.*;
import Benchmark.Formula.*;
import Benchmark.Formula.Nguyen.*;
import Space.Tree;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;


/**
 * Created by Pouya Payandeh on 2/26/2016.
 */
class JArg
{
    @Parameter(names = "-ro")
     double ro = 0.2;
    @Parameter(names = "-ros")
     double ros = 0.1;
    @Parameter(names = "-rosd")
     double rosd = 0.1;
    @Parameter(names = "-tra")
     double tra = 1.0;
    @Parameter(names = "-trb")
     double trb = 0.01;
    @Parameter(names = "-trc")
     double trc = 1.0;
    @Parameter(names = "-at")
     double at = 1;
    @Parameter(names = "-bt")
     double bt = 0.01;
    @Parameter(names = "-ct")
     double ct = 1;
    @Parameter(names = "-initv")
     double initv = 0;
    @Parameter(names = "-initl")
    double initl = 0;
    @Parameter(names = "-rei")
    int rei = 100;
    public void setWD()
    {
        WD.at = at;
        WD.bt = bt;
        WD.ct = ct;
        WD.tra = tra;
        WD.trb = trb;
        WD.trc = trc;

    }
}
public class CLIMain {

    static int countOfEachSymbol = 10;
    public static void main(String[] args) {
        //-initv 3 -rei 5 -initl 1000 -ro 0.1 -ros 0.1 -rosd 0.1 -trc 0.1 -ct 0.1 -trb 1 -at 1
        JArg arg = new JArg();
        int c =1;
//        arg.initv = Integer.parseInt(args[c]);
//        arg.rei = Integer.parseInt(args[c+2]);
//        arg.initl = Integer.parseInt(args[c+4]);
//        arg.ro = Double.parseDouble(args[c+6]);
//        arg.ros = Double.parseDouble(args[c+8]);
//        arg.rosd = Double.parseDouble(args[c+10]);
//        arg.trc = Double.parseDouble(args[c+12]);
//        arg.ct = Double.parseDouble(args[c+14]);
//        arg.trb = Integer.parseInt(args[c+16]);
//        arg.at = Integer.parseInt(args[c+18]);
        new JCommander(arg,args);
        NguyenBenchmark benchmark= new NguyenBenchmark();
        //ROUXBenchmark benchmark = new ROUXBenchmark();
        benchmark.setFormula(new F10());
        HUD.init(benchmark.getFormula());
//        CompleteGraphMultiSymbol antCompleteGraphMultiSymbol =
//                new CompleteGraphMultiSymbol(arg.initv, benchmark.getFormula(), getCountOfEachSymbol());
        Tree tree = new Tree(arg.initl,benchmark.getFormula());
        WD[] agents = new WD[benchmark.getAgentsN()];

        for (int i = 0; i < agents.length; i++) {
            agents[i] = AgentBuilder.createWDTree(arg.initl, tree, benchmark, arg.initv);
        }

        MetaHeuristic.ro = arg.ro;
        MetaHeuristic.ros = arg.ros;
        MetaHeuristic.rosd = arg.rosd;
        MetaHeuristic.reInitialItrNo=arg.rei;
        IWDP WDCGMS = new IWDP(tree,agents, arg.initl,benchmark);
        benchmark.printResults();

    }

    public static int getCountOfEachSymbol() {
        return countOfEachSymbol;
    }
}
