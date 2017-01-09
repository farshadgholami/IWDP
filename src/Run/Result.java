package Run;

/**
 * Created by Pouya Payandeh on 11/28/2016.
 */
public class Result
{
    Arg arg;
    public double min;
    public double max;
    double avg;
    double adjustedAvg;
    public double meanBest;

    int successfulRuns = -1;
    String best;
    int turn;

    public void setSuccessfulRuns(int successfulRuns) {
        this.successfulRuns = successfulRuns;
    }


    public Arg getArg()
    {
        return arg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("arg=").append(arg);
        sb.append("\n, min=").append(min);
        sb.append("\n, max=").append(max);
        sb.append("\n, avg=").append(avg);
        sb.append("\n, adjustedAvg=").append(adjustedAvg);
        sb.append("\n, meanBest=").append(meanBest);
        sb.append("\n, successfulRuns=").append(successfulRuns);
        sb.append("\n, best='").append(best).append('\'');
        sb.append("\n, turn=").append(turn);
        sb.append('}');
        return sb.toString();
    }

    public Result(Arg arg, double min, double max, double avg, double adjustedAvg, double meanBest, String best, int turn) {
        this.arg = arg;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.adjustedAvg = adjustedAvg;
        this.meanBest = meanBest;

        this.best = best;
        this.turn = turn;
    }
}