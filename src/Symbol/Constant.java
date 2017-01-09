package Symbol;

/**
 * Created by Pouya Payandeh on 1/26/2016.
 */
public class Constant extends GSymbol {
    private double constantVal;

    public Constant(double constantVal) {

        this.constantVal = constantVal;
        symbolWeight = -1;
        presentation = String.valueOf(constantVal);
        namOperand = 0;
        terminal = true;
        constant = true;
    }

    public double exe() {
        return constantVal;
    }

    public GSymbol Clone() {
        return new Constant(constantVal);
    }
}
