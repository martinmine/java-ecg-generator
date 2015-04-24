package no.hig.imt3591.id3;

import no.hig.imt3591.id3.nodes.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for a decision tree, used to find or (somewhat) predict the outcome
 * of an event by creating the decision tree using a data set to build (train) it.
 */
public class DecisionTree {
    private DecisionNode root;
    private int fields;
    private IAttributeNameProvider nameProvider;
    private IAttributeMetadataProvider metadataProvider;

    /**
     * Creates a new decision tree using the ID3 algorithm.
     * @param set The data-set to train (build) the decision tree. This has to be an array
     *            where the last integer in the value is true/false.
     * @param fields The length fields/indexes of the integer array should be used in the set. Count of attributes.
     * @param nameProvider Provides values for attributes that is not continuous.
     * @param metadataProvider Provides metadata about each attribute.
     */
    public DecisionTree(List<Observation> set, int fields,
                        IAttributeNameProvider nameProvider, IAttributeMetadataProvider metadataProvider) {
        this.fields = fields;
        this.nameProvider = nameProvider;
        this.metadataProvider = metadataProvider;
        this.root = split(set, new LinkedList<>());
    }

    /**
     * Performs a search through the tree and finds an.
     * @param tuple The data that will be tested against the tree.
     * @return The output value of the data-set.
     */
    public ITreeResult search(Observation tuple) {
        return root.search(tuple);
    }

    /**
     * Splits the tree using the ID3 algorithm. This algorithm has been modified so it
     * also operates on continuous values.
     * See http://en.wikipedia.org/wiki/ID3_algorithm
     * @param set Current working subset
     * @param closedList Attributes that has been plotted in the tree.
     * @return New node in the tree with its children
     */
    private DecisionNode split(List<Observation> set, List<Integer> closedList) {
        double lowestGain = 0;
        int lowestGainField = 0;
        Entropy setEntropy = new Entropy(set);
        int setSize = set.size();
        double threshold = 0;

        /**
         * Find the node with the highest overall gain
         */
        for (int i = 0; i < fields; i++) {
            if (!closedList.contains(i)) {
                final int fieldType = metadataProvider.getAttributeType(i);
                double subsetEntropy = setEntropy.getEntropy();

                if (fieldType == IAttributeMetadataProvider.CATEGORICAL) {
                    Double[] fieldValues = nameProvider.getAttributeValues(i);
                    for (double fieldValue : fieldValues) {
                        Entropy fieldEntropy = new Entropy(set, i, fieldValue);
                        subsetEntropy -= getInformationGain(fieldEntropy, setSize);
                    }
                } else if (fieldType == IAttributeMetadataProvider.CONTINUOUS) {
                    threshold = findLowestSplit(set, i);
                    Entropy.EntropySet setSplit = Entropy.filterByThreshold(set, i, threshold);
                    subsetEntropy -= getInformationGain(setSplit.left, setSize);
                    subsetEntropy -= getInformationGain(setSplit.right, setSize);
                }

                if (subsetEntropy > lowestGain) { // !!
                    lowestGain = subsetEntropy;
                    lowestGainField = i;
                }
            }
        }

        DecisionNode node = new DecisionNode(lowestGainField);
        closedList.add(lowestGainField);

        /**
         * Then we expand the values for the node with the lowest entropy/highest information gain
         */
        int attributeType = metadataProvider.getAttributeType(lowestGainField);
        if (attributeType == IAttributeMetadataProvider.CATEGORICAL) {
            for (double name : nameProvider.getAttributeValues(lowestGainField)) {
                List<Observation> subset = filterSet(set, lowestGainField, name);
                Entropy trueFalseSubsetCount = new Entropy(subset);

                if (trueFalseSubsetCount.getEntropy() == 0) {
                    node.addChild(new LeafNode(name, subset.get(0).createInstance()));
                } else {
                    node.addChild(new AttributeNode(name, split(subset, new LinkedList<>(closedList))));
                }
            }
        } else if (attributeType == IAttributeMetadataProvider.CONTINUOUS){
            List<Observation> subsetA = new LinkedList<>();
            List<Observation> subsetB = new LinkedList<>();

            // Filter at threshold
            for (Observation observation : set) {
                (observation.getTuple()[lowestGainField] <= threshold ? subsetA : subsetB).add(observation);
            }

            if (subsetA.size() > 0) {
                if (new Entropy(subsetA).getEntropy() == 0) {
                    node.addChild(new AlphaLeafNode(threshold, subsetA.get(0).createInstance()));
                } else {
                    node.addChild(new AlphaAttributeNode(threshold, split(subsetA, new LinkedList<>(closedList))));
                }
            }

            if (subsetB.size() > 0) {
                if (new Entropy(subsetB).getEntropy() == 0) {
                    node.addChild(new BetaLeafNode(threshold, subsetB.get(0).createInstance()));
                } else {
                    node.addChild(new BetaAttributeNode(threshold, split(subsetB, new LinkedList<>(closedList))));
                }
            }
        }

        return node;
    }

    /**
     * Finds the threshold to split on that gives the most information gain from a set.
     * @param set Set to find threshold value from.
     * @param index The index of the attribute that will be.
     * @return The threshold value that gives the most information gain.
     */
    private double findLowestSplit(List<Observation> set, int index) {
        double lowestEntropy = 1;
        double lowestThreshold = 0;

        for (int i = 1; i < set.size(); i++) {
            double threshold = set.get(i).getTuple()[index];
            Entropy.EntropySet entropy = Entropy.filterByThreshold(set, index, threshold);

            double subsetEntropy = (entropy.left.getEntropy() + entropy.right.getEntropy()) / 2;

            if (lowestEntropy > subsetEntropy) {
                lowestThreshold = threshold;
                lowestEntropy = subsetEntropy;
            }
        }

        return lowestThreshold;
    }

    /**
     * Calculates information gain from one set,
     * @param entropy The entropy from the set.
     * @param superSetSize The size of the parent set.
     * @return Information gain.
     */
    private double getInformationGain(Entropy entropy, double superSetSize) {
        return entropy.getEntropySize() / superSetSize * entropy.getEntropy();
    }

    /**
     * Gets a subset out of parent set where a condition is true in the parent set.
     * @param set Parent set.
     * @param field Attribute index that will be compared against the condition.
     * @param condition The value that has to be equals to in the attribute.
     * @return Subset of set.
     */
    private List<Observation> filterSet(List<Observation> set, int field, double condition) {
        return set.stream().filter(observation -> observation.getTuple()[field].equals(condition)).collect(Collectors.toCollection(LinkedList::new));
    }
}
