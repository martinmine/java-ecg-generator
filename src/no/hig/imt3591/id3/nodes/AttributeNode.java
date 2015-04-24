package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.Observation;

/**
 * Concrete test on an categorical attributes.
 */
public class AttributeNode {
    private double value;
    private DecisionNode next;

    public AttributeNode(double value, DecisionNode next) {
        this.value = value;
        this.next = next;
    }

    public boolean canVisit(double visitor) {
        return visitor == value;
    }

    public boolean visit(Observation set) {
        return next.search(set);
    }

    protected double getValue() {
        return value;
    }
}
