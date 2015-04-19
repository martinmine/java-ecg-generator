package no.hig.imt3591.id3.nodes;

import java.util.LinkedList;
import java.util.List;

/**
 * Node that will make a decision and has a set of children with their conditions.
 * Contains information about which attribute to test against and references to its children.
 */
public class DecisionNode {
    private int attribute;
    private List<AttributeNode> children;

    public DecisionNode(int attribute) {
        this.attribute = attribute;
        this.children = new LinkedList<>();
    }

    /**
     * Adds a child to the node.
     * @param child New child.
     */
    public void addChild(AttributeNode child) {
        this.children.add(child);
    }

    /**
     * Searches through the tree.
     * @param set Tuple to test against.
     * @return Node test output, the decision in the decision tree.
     */
    public boolean search(int[] set) {
        for (AttributeNode attributeNode : children) {
            if (attributeNode.canVisit(set[attribute])) {
                return attributeNode.visit(set);
            }
        }

        return false;
    }
}