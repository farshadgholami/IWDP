package Symbol;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Created by Farshad PC on 10/4/2015.
 */
public class KeijzerConstant extends GSymbol {

    private double constantVal;

    public KeijzerConstant() {

        NormalDistribution nd = new NormalDistribution(0, 5);
        constantVal = nd.sample();
        presentation = String.valueOf(constantVal);
        symbolWeight = -1;
        namOperand = 0;
        terminal = true;
        constant = true;
    }

    public double exe() {
        return constantVal;
    }

    public GSymbol Clone() {
        return new KeijzerConstant();
    }
}
