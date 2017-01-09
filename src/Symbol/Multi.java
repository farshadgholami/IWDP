package Symbol;

/**
 * Created by Farshad PC on 9/14/2015.
 */

public class Multi extends GSymbol {
    public Multi() {
        presentation = "*";
        symbolWeight = 1;
        namOperand = 2;
        terminal = false;
    }

    public double exe(double firstOprand, double secondOprand) {
        return (firstOprand * secondOprand);
    }
}