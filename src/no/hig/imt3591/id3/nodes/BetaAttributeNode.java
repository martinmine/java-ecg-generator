package no.hig.imt3591.id3.nodes;

/**
 * A node that goes to the right on a test on a continuous attribute.
 */
public class BetaAttributeNode extends AttributeNode {
    public BetaAttributeNode(final double value, final DecisionNode next) {
        super(value, next);
    }

    @Override
    public boolean canVisit(final double visitor) {
        return visitor > super.getValue();
    }
}
