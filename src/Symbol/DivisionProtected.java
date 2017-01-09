package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class DivisionProtected extends GSymbol {
    public DivisionProtected() {
        presentation = "./";
        symbolWeight = 1;
        namOperand = 2;
        terminal = false;
    }

    public double exe(double firstOprand, double secondOprand) {

        double result;

        if (secondOprand == 0) {
            result = 1;
        } else {
            result = firstOprand / secondOprand;
        }
        return result;
    }
}
