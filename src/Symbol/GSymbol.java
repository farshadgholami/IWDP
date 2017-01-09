package Symbol;

/**
 * Created by Farshad PC on 9/14/2015.
 */
public class GSymbol {
    //How to show symbol in Space.Tree
    String presentation;
    //Vazn har symbol baraye mohasebeye tedade terminal va non-Terminal morede niaz
    int symbolWeight;
    //number of oprand need
    int namOperand;
    boolean constant = false;
    boolean terminal = false;

    public boolean isFunc() {
        return isFunc;
    }

    boolean isFunc = false;
    int variableNum;

    public GSymbol() {
        presentation = "F";
        symbolWeight = 0;
        namOperand = -1;
        terminal = true;
    }

    public GSymbol(GSymbol symbol) {
        this.presentation = symbol.getPresentation();
        this.symbolWeight = symbol.getSymbolWeight();
        this.namOperand = symbol.getNamOperand();
        this.constant = symbol.isConstant();
        this.terminal = symbol.isTerminal();
        this.variableNum = symbol.getVariableNum();
    }

    public GSymbol(int variableNum) {
        presentation = "x" + variableNum;
        symbolWeight = -1;
        namOperand = 0;
        terminal = true;
        this.variableNum = variableNum;
    }


    public int getVariableNum() {
        return variableNum;
    }

    public String getPresentation() {
        return presentation;
    }

    public int getSymbolWeight() {
        return symbolWeight;
    }

    public int getNamOperand() {
        return namOperand;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public boolean isConstant() {
        return constant;
    }

    public double exe(double firstOprand, double secondOprand) {
        return 0;
    }

    public double exe(double parameter) {
        return parameter;
    }

    public double exe() {
        return 0;
    }
}