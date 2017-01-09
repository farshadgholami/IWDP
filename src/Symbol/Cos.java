package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Cos extends GSymbol {
    public Cos() {
        presentation = "Cos";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
        isFunc = true;
    }

    public double exe(double parameter) {
        return Math.cos(parameter);
    }
}
