package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Negative extends GSymbol {
    public Negative() {
        presentation = "-1*";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
    }
    @Override
    public double exe(double parameter) {
        return (-1 * parameter);
    }
}
