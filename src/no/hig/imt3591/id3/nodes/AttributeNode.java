package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.ITreeResult;
import no.hig.imt3591.id3.Observation;

/**
 * Concrete test on an categorical attributes.
 */
public class AttributeNode {
    private final double value;
    private final DecisionNode next;

    public AttributeNode(final double value, final DecisionNode next) {
        this.value = value;
        this.next = next;
    }

    public boolean canVisit(final double visitor) {
        return visitor == value;
    }

    public ITreeResult visit(final Observation set) {
        return next.search(set);
    }

    double getValue() {
        return value;
    }
}
