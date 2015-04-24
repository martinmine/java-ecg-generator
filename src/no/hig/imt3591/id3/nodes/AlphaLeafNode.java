package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.ITreeResult;

/**
 * Leaf node used on a test on a continuous attribute that ends with a leaf value.
 */
public class AlphaLeafNode extends LeafNode {
    public AlphaLeafNode(double value, ITreeResult endValue) {
        super(value, endValue);
    }

    @Override
    public boolean canVisit(double visitor) {
        return visitor <= super.getValue();
    }
}
