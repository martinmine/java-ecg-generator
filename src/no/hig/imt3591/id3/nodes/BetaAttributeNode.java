package no.hig.imt3591.id3.nodes;

/**
 * A node that goes to the right on a test on a continuous attribute.
 */
public class BetaAttributeNode extends AttributeNode {
    public BetaAttributeNode(double value, DecisionNode next) {
        super(value, next);
    }

    @Override
    public boolean canVisit(double visitor) {
        return visitor > super.getValue();
    }
}
