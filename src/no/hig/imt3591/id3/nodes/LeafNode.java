package no.hig.imt3591.id3.nodes;

/**
 * Terminal node of the tree.
 */
public class LeafNode extends AttributeNode {
    private boolean endValue;

    public LeafNode(int value, boolean endValue) {
        super(value, null);
        this.endValue = endValue;
    }

    @Override
    public boolean visit(int[] set) {
        return endValue;
    }
}
