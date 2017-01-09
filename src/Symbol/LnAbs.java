package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class LnAbs extends GSymbol {
    public LnAbs() {
        presentation = "LnAbs";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
        isFunc = true;
    }

    public double exe(double parameter) {
        return Math.log(Math.abs(parameter));
    }
}
