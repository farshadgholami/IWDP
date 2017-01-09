package Run;

import Agent.WD;

/**
 * Created by Pouya Payandeh on 11/28/2016.
 */
public class Arg
{
    @RandomParameter(lowerBound = 0.1, upperBound = 0.9)
    public double ro = 0.2;
    @RandomParameter(lowerBound = 0.1, upperBound = 0.9)
    public double ros = 0.1;
    @RandomParameter(lowerBound = 0.1, upperBound = 0.9)
    public double rosd = 0.1;

    public double tra = 1.0;
    @RandomParameter(lowerBound = 1, upperBound = 1000)
    public double trb = 0.01;
    @RandomParameter(lowerBound = 0.1, upperBound = 0.9)
    public double trc = 1.0;
    @RandomParameter(lowerBound = 1, upperBound = 200)
    public double at = 1;

    public double bt = 0.01;
    @RandomParameter(lowerBound = 0.1, upperBound = 0.9)
    public double ct = 1;
    @RandomParameter(lowerBound = 3 , upperBound = 30)
    public double initv = 0;
    @RandomParameter(lowerBound = 1000 ,upperBound = 10000)
    public double initl = 0;

    //@Run.RandomParameter(lowerBound = 1000 ,upperBound = 10000)
    public double minLearnValue = 0.01;

    @RandomParameter(lowerBound = 5 , upperBound = 50)
    public int rei = 0;
    public void setWD()
    {
        WD.at = at;
        WD.bt = bt;
        WD.ct = ct;
        WD.tra = tra;
        WD.trb = trb;
        WD.trc = trc;

    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Arg{");
        sb.append("ro=").append(ro);
        sb.append(", ros=").append(ros);
        sb.append(", rosd=").append(rosd);
        sb.append(", tra=").append(tra);
        sb.append(", trb=").append(trb);
        sb.append(", trc=").append(trc);
        sb.append(", at=").append(at);
        sb.append(", bt=").append(bt);
        sb.append(", ct=").append(ct);
        sb.append(", initv=").append(initv);
        sb.append(", initl=").append(initl);
        sb.append(", rei=").append(rei);
        sb.append('}');
        return sb.toString();
    }
}