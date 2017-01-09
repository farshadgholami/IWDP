package Benchmark;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pouya Payandeh on 2/28/2016.
 */
public class ROUXBenchmark extends Benchmark
{
    ArrayList<Double> fitnessArray = new ArrayList();

    public ROUXBenchmark() {
//        runsN = 40;
//        iterationsN = 50;
//        agentsN = 500;
        runsN = 10;
        iterationsN = 500;
        agentsN = 100;
    }

    @Override
    public double getFitness(ArrayList<Double> values) {
        double raw = getRawFitness(values);
        double c = 1.0/(1+raw);
        if (!Double.isNaN(c))
        {
            fitnessArray.add(c);
        }

        return raw;
    }

    @Override
    public void printResults()
    {
        System.out.printf("Best :%E \n" ,getBest());
        System.out.printf("Average :%E \n",getAverage());
        System.out.printf("Worst :%E \n",getWorst());
    }

    public double getRawFitness(ArrayList<Double> values)
    {
        int trainSetSize = f.getTrainingSetSize();

        double sum = 0;
        for (int index = 0; index < trainSetSize; index++) {

            sum += Math.abs(values.get(index) - f.getExpectedTrainResult(index));
            if(Double.isInfinite(sum))
            {
                sum = Double.MAX_VALUE;
                break;
            }
        }

        return (1.0/trainSetSize)*sum;
    }
    public double getBest()
    {
        return Collections.max(fitnessArray);
    }
    public  double getWorst()
    {
        return Collections.min(fitnessArray);
    }
    public double getAverage()
    {
       // double sum = fitnessArray.parallelStream().reduce((aDouble, aDouble2) -> aDouble+aDouble2).get();
        double sum =0;
        for(double a : fitnessArray)
            sum+=a;
        return sum/fitnessArray.size();
    }
}
