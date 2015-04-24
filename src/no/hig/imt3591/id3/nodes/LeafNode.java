package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.ITreeResult;
import no.hig.imt3591.id3.Observation;

/**
 * Terminal node of the tree.
 */
public class LeafNode extends AttributeNode {
    private ITreeResult endValue;

    public LeafNode(double value, ITreeResult endValue) {
        super(value, null);
        this.endValue = endValue;
    }

    @Override
    public ITreeResult visit(Observation set) {
        return endValue;
    }
}
