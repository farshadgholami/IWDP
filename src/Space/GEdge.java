package Space;

/**
 * Created by farshad on 11/2/15.
 */
//Graph Edge
public class GEdge {

    Node firstNode, secondNode;
    double learnValue;

    public GEdge(Node firstNode, Node secondNode, double initLearnValue) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        learnValue = initLearnValue;
    }

    public Node getFirstNode()
    {
        return firstNode;
    }

    public double getLearnValue() {
        return learnValue;
    }

    public void setLearnValue(double learnValue) {
        this.learnValue = learnValue;
    }

    //Space.Node yek sare edge ro migire, node e sare dige ro mide
    public Node getSecondNode(Node firstNode) {
        if (this.firstNode.equals(firstNode))
            return this.secondNode;
        else
            return this.firstNode;
    }

    public Node getSecondNode() {
        return this.secondNode;
    }
}

