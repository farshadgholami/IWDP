package Benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Pouya Payandeh on 2/28/2016.
 */
public class NguyenBenchmark extends Benchmark
{
    ArrayList<Double> fitnessArray = new ArrayList();
    ArrayList<Double> adjustedFitnessArray = new ArrayList();

    boolean []data;
    Double[][][] fitnessEachAgents;
    int[] counter = new int[3];
    public NguyenBenchmark() {

        runsN = 100;
        iterationsN = 50;
        agentsN = 500;
    }

    @Override
    public double getFitness(ArrayList<Double> values) {
        if(data == null)
            data = new boolean[runsN];
        if(fitnessEachAgents == null)
            fitnessEachAgents = new Double[runsN][iterationsN][agentsN];

        double raw = getRawFitness(values);
        /*fitness ro mosavie rawFitness gharar dadam. baad ye adjustedFitness ezafe kardam.
        * chon inja ro taghr dadam va adjusted ro hazf kardam, bayad function getWorst, getBest va getMeanBest
        * ro ham taghir bedim
        */

        if (!Double.isNaN(raw)) {
            fitnessArray.add(raw);
            fitnessEachAgents[counter[2]][counter[1]][counter[0]] = raw;
        } else {
            fitnessEachAgents[counter[2]][counter[1]][counter[0]] = Double.MAX_VALUE;
        }

        double c = 1.0 / (1 + raw);
        if (!Double.isNaN(c))
            adjustedFitnessArray.add(c);

        counter[0]++;
        counter[1]+=counter[0]/agentsN;
        counter[0]%=agentsN;
        counter[2]+=counter[1]/iterationsN;
        counter[1]%=iterationsN;

        if (!Double.isNaN(raw)) {
            return raw;
        }
        return Double.MAX_VALUE;
    }

    @Override
    public void printResults()
    {
        System.out.printf("Average: %f \n",getAverage());
    }

    public double getRawFitness(ArrayList<Double> values) {
        int trainSetSize = f.getTrainingSetSize();

        double sum = 0;
        boolean isSuccessfull = true;
        for (int index = 0; index < trainSetSize; index++) {
            double err = Math.abs(values.get(index) - f.getExpectedTrainResult(index));
            if(err > 0.3 || Double.isNaN(err))
                isSuccessfull = false;
            sum += err;
        }
        data[counter[2]] |=isSuccessfull;
        if(Double.isInfinite(sum))
            sum = Double.MAX_VALUE;

        return sum;
    }
    public double getBest()
    {
        return Collections.min(fitnessArray);
    }
    public  double getWorst()
    {
        return Collections.max(fitnessArray);
    }
    public double getAverage() {
       // double sum = fitnessArray.parallelStream().reduce((aDouble, aDouble2) -> aDouble+aDouble2).get();
        double sum = 0;
        for(double a : fitnessArray)
            sum += a;
        return sum / fitnessArray.size();
    }
    public double getAdjustedAverage() {
        double sum = 0;
        for(double a : adjustedFitnessArray)
            sum += a;
        return sum / adjustedFitnessArray.size();
    }

    public double getMeanBest() {
        double sum = 0;
        ArrayList<Double> minArray = new ArrayList<>();
        for (int i = 0; i < runsN; i++) {
            for (int j = 0; j < iterationsN; j++) {
                minArray.add(Collections.min(Arrays.asList(fitnessEachAgents[i][j])));
            }
            sum += Collections.min(minArray);
            minArray.clear();
        }
        return sum / runsN;
    }

    public int getSuccessfulRun() {
        int count = 0 ;
        for(int i = 0 ; i < runsN ; i++) {
            if(data[i])
                count++;
        }
        return count;
    }

    private boolean isFitnessCaseSuccessful(double[] doubles) {
        for(int i = 0 ; i <  doubles.length ; i++)
            if(doubles[i] > 0.3 || Double.isNaN(doubles[i]))
                return false;
        return true;
    }
}
