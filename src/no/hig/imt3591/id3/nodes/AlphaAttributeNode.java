package no.hig.imt3591.id3.nodes;

/**
 * Test on a continuous attribute which goes to the left in the decision tree if the condition is met.
 */
public class AlphaAttributeNode extends AttributeNode {
    public AlphaAttributeNode(double value, DecisionNode next) {
        super(value, next);
    }

    @Override
    public boolean canVisit(double visitor) {
        return visitor <= super.getValue();
    }
}
