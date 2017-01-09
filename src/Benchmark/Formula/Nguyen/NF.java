package Benchmark.Formula.Nguyen;

import Benchmark.Formula.Formula;
import Symbol.*;

/**
 * Created by farsh on 11/20/2016.
 */
public class NF extends Formula{
    public NF() {
        nonTerminalSet = new GSymbol[]{
                new Plus(),
                new Minus(),
                new DivisionProtected(),
                new Multi(),
                new Sin(),
                new Cos(),
                new Exponential(),
                new LogProtected()
        };
    }
}
