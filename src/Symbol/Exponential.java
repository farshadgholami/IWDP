package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Exponential extends GSymbol {
    public Exponential() {
        presentation = "Exp";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
        isFunc = true;
    }

    public double exe(double parameter) {
        double p = Math.exp(parameter);
//        if (Double.isNaN(p)) {
//            return Double.MAX_VALUE;
//        }
//        if (Double.isInfinite(p)) {
//            return Double.MAX_VALUE;
//        }
        return p;
    }
}
