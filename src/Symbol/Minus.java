package Symbol;

/**
 * Created by Farshad PC on 9/14/2015.
 */

public class Minus extends GSymbol {
    public Minus() {
        presentation = "-";
        symbolWeight = 1;
        namOperand = 2;
        terminal = false;
    }
    @Override
    public double exe(double firstOprand, double secondOprand) {
        return (firstOprand - secondOprand);
    }
}