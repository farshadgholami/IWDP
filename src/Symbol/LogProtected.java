package Symbol;

/**
 * Created by farsh on 11/20/2016.
 */
public class LogProtected extends GSymbol {
    public LogProtected() {
        presentation = "RLog";
        symbolWeight = 0;
        namOperand = 1;
        terminal = false;
    }
    @Override
    public double exe(double parameter) {
        if (parameter == 0) {
            return 0;
        }
        return Math.log(Math.abs(parameter));
    }
}
