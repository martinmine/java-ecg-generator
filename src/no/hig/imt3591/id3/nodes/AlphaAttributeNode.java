package no.hig.imt3591.id3.nodes;

/**
 * Test on a continuous attribute which goes to the left in the decision tree if the condition is met.
 */
public class AlphaAttributeNode extends AttributeNode {
    public AlphaAttributeNode(final double value, final DecisionNode next) {
        super(value, next);
    }

    @Override
    public boolean canVisit(final double visitor) {
        return visitor <= super.getValue();
    }
}
