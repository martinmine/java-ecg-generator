package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.Observation;

/**
 * Terminal node of the tree.
 */
public class LeafNode extends AttributeNode {
    private boolean endValue;

    public LeafNode(double value, boolean endValue) {
        super(value, null);
        this.endValue = endValue;
    }

    @Override
    public boolean visit(Observation set) {
        return endValue;
    }
}
