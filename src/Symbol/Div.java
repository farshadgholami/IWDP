package Symbol;

/**
 * Created by Farshad PC on 9/14/2015.
 */


public class Div extends GSymbol {
    public Div() {
        presentation = "/";
        symbolWeight = 1;
        namOperand = 2;
        terminal = false;
    }
    @Override
    public double exe(double firstOprand, double secondOprand) {
        return (firstOprand / secondOprand);
    }
}