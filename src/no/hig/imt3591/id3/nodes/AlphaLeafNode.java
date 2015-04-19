package no.hig.imt3591.id3.nodes;

/**
 * Leaf node used on a test on a continuous attribute that ends with a leaf value.
 */
public class AlphaLeafNode extends LeafNode {
    public AlphaLeafNode(int value, boolean endValue) {
        super(value, endValue);
    }

    @Override
    public boolean canVisit(int visitor) {
        return visitor <= super.getValue();
    }
}
