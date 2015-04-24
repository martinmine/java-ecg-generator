package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.ITreeResult;

/**
 * A terminal node that goes to the right when it is a terminal/leaf node.
 */
public class BetaLeafNode extends LeafNode {
    public BetaLeafNode(double value, ITreeResult endValue) {
        super(value, endValue);
    }

    @Override
    public boolean canVisit(double visitor) {
        return visitor > super.getValue();
    }
}
