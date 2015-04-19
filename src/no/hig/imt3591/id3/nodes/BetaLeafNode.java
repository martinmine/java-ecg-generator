package no.hig.imt3591.id3.nodes;

/**
 * A terminal node that goes to the right when it is a terminal/leaf node.
 */
public class BetaLeafNode extends LeafNode {
    public BetaLeafNode(int value, boolean endValue) {
        super(value, endValue);
    }

    @Override
    public boolean canVisit(int visitor) {
        return visitor > super.getValue();
    }
}
