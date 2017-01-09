package Symbol;

/**
 * Created by Farshad PC on 9/30/2015.
 */
public class PowerTwo extends GSymbol {

    public PowerTwo() {
        presentation = "powerTwo";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
    }

    public double exe(double parameter) {
        return Math.pow(parameter, 2);
    }
}
