package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class Sin extends GSymbol {
    public Sin() {
        presentation = "Sin";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
        isFunc = true;
    }

    public double exe(double parameter) {
        return Math.sin(parameter);
    }
}
