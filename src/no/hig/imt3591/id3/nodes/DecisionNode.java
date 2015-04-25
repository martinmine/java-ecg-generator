package no.hig.imt3591.id3.nodes;

import no.hig.imt3591.id3.ITreeResult;
import no.hig.imt3591.id3.Observation;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Node that will make a decision and has a set of children with their conditions.
 * Contains information about which attributeIndex to test against and references to its children.
 */
public class DecisionNode<T> {
    private final Field attributeField;
    private final List<AttributeNode> children;

    /**
     * Creates a new decision node.
     * @param attributeField Index of the attribute that this node is for.
     */
    public DecisionNode(final Field attributeField) {
        this.attributeField = attributeField;
        this.children = new LinkedList<>();
    }

    /**
     * Adds a child to the node.
     * @param child New child.
     */
    public void addChild(final AttributeNode child) {
        this.children.add(child);
    }

    /**
     * Searches through the tree.
     * @param set Tuple to test against.
     * @return Node test output, the decision in the decision tree.
     */
    public ITreeResult search(final Observation<T> set) {
        for (final AttributeNode attributeNode : children) {
            if (attributeNode.canVisit(set.getObservationValue(attributeField))) {
                return attributeNode.visit(set);
            }
        }

        // TODO: Handle this, could throw exception
        return null;
    }
}