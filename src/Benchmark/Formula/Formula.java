package Benchmark.Formula;

import Symbol.*;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.UniformRealDistribution;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Formula {
    public String name;
    public int vars;
    //int numberofConstant = 0;
    public ArrayList<double[]> trainingSet;
    public ArrayList<double[]> testingSet;

    private double[] expectedTrainResult;
    private double[] expectedTestResult;

    //Random rand = new Random();

    private int numberOfNode;

    public GSymbol[] nonTerminalSet;
    public GSymbol[] constantSet;

    public boolean isKeijzer() {
        return name.toLowerCase().startsWith("keijzer");
    }

    public int getNumberOfConstant() {
        if(constantSet != null)
            return  constantSet.length;
        return  0;
    }
    public int getNumberOfNode() {
        return this.numberOfNode;
    }

    public void calcuteNumberOfNode() {
        int numberOfNode = getVars() + getNonTerminalSet().length + getNumberOfConstant();

        if (isKeijzer())
            numberOfNode++;

        this.numberOfNode =  numberOfNode;
    }

    public GSymbol getConstant(int i)
    {
        if(constantSet == null || i >= constantSet.length)
            return null;
        return constantSet[i];
    }
    public int getTrainingSetSize() {
        return trainingSet.size();
    }

    public int getTestingSetSize() {
        return testingSet.size();
    }

    public double[] getExpectedTrainResult() {
        return expectedTrainResult;
    }

    public double getExpectedTrainResult(int index) {
        return expectedTrainResult[index];
    }

    public double[] getExpectedTestResult() {
        return expectedTestResult;
    }

    public double getExpectedTestResult(int index) {
        return expectedTestResult[index];
    }

    public int getVars() {
        return vars;
    }

    public String getName() {
        return name;
    }

    public GSymbol[] getNonTerminalSet() {
        return nonTerminalSet;
    }

    public double objectiveFunction(double[] val) {
        return 0;
    }

    public void calculateExpectedResult() {

        expectedTrainResult = new double[trainingSet.size()];
        expectedTestResult = new double[testingSet.size()];
        double[] trainingValues;
        double[] testingValues;

        for (int index = 0; index < trainingSet.size(); index++) {
            trainingValues = getTrainingValues(index);
            expectedTrainResult[index] = objectiveFunction(trainingValues);
        }

        for (int index = 0; index < testingSet.size(); index++) {
            testingValues = getTestingValues(index);
            expectedTestResult[index] = objectiveFunction(testingValues);
        }
    }

    public double[] getTrainingValues(int index) {
        return trainingSet.get(index);
    }

    public double[] getTestingValues(int index) {
        return testingSet.get(index);
    }

    public ArrayList<double[]> uniformRandom(double start, double end, double c) {

        UniformRealDistribution uniformRealDistribution = new UniformRealDistribution(start, end);
        ArrayList<double[]> result;
        ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i < vars; i++) {

            temp.add(new ArrayList<Double>());
            for (int index = 0; index < c; index++) {
                //double n = rand.getDoubleRandom();
                double n = uniformRealDistribution.sample();
                temp.get(i).add(n);
            }
        }

        result = fixPack(temp);
        return result;
    }

    public ArrayList<double[]> evenlySpaced(double start, double end, double c) {
        
        ArrayList<double[]> result;
        ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
        
        for (int i = 0; i < vars; i++) {
            temp.add(new ArrayList<Double>());
            double passed = start;
            while (passed <= end) {
                temp.get(i).add(passed);
                passed += c;
            }
        }

        result = fixPack(temp);
        return result;
    }
    public ArrayList<double[]> linearlySpaced(double start, double end, int c) {

        double step = (end -start) / c;
        return evenlySpaced(start,end,step);
    }

    private ArrayList<double[]> fixPack(ArrayList<ArrayList<Double>> pack) {
        ArrayList<double[]> result = new ArrayList<double[]>();

        for (int i = 0; i < pack.get(0).size(); i++) {
            result.add(new double[vars]);
            for (int j = 0; j < vars; j++) {
                result.get(i)[j] = pack.get(j).get(i);
            }
        }
        return result;
    }
}