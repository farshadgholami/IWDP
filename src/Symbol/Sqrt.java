package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Sqrt extends GSymbol {
    public Sqrt() {
        presentation = "sqrt";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
        isFunc = true;
    }

    public double exe(double parameter) {
        return Math.sqrt(Math.abs(parameter));
    }
}
