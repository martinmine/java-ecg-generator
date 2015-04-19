package no.hig.imt3591.id3.nodes;

/**
 * Concrete test on an categorical attributes.
 */
public class AttributeNode {
    private int value;
    private DecisionNode next;

    public AttributeNode(int value, DecisionNode next) {
        this.value = value;
        this.next = next;
    }

    public boolean canVisit(int visitor) {
        return visitor == value;
    }

    public boolean visit(int[] set) {
        return next.search(set);
    }

    protected int getValue() {
        return value;
    }
}
