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
    private final DecisionNode root;
    private final IAttributeNameProvider nameProvider;
    private final IAttributeMetadataProvider metadataProvider;
    private final int fields;

    /**
     * Creates a new decision tree using the ID3 algorithm.
     * @param set The data-set to train (build) the decision tree. This has to be an array
     *            where the last integer in the value is true/false.
     * @param fields The length fields/indexes of the integer array should be used in the set. Count of attributes.
     * @param nameProvider Provides values for attributes that is not continuous.
     * @param metadataProvider Provides metadata about each attribute.
     */
    public DecisionTree(final List<Observation> set, final int fields,
                        final IAttributeNameProvider nameProvider, final IAttributeMetadataProvider metadataProvider) {
        this.fields = fields;
        this.nameProvider = nameProvider;
        this.metadataProvider = metadataProvider;
        this.root = split(set);
    }

    /**
     * Performs a search through the tree and finds an.
     * @param tuple The data that will be tested against the tree.
     * @return The output value of the data-set.
     */
    public ITreeResult search(final Observation tuple) {
        return root.search(tuple);
    }

    /**
     * Splits the tree using the ID3 algorithm. This algorithm has been modified so it
     * also operates on continuous values.
     * See http://en.wikipedia.org/wiki/ID3_algorithm
     * @param set Current working subset
     * @return New node in the tree with its children
     */
    private DecisionNode split(final List<Observation> set) {
        final Entropy setEntropy = new Entropy(set);
        final int setSize = set.size();

        double highestGain = 0;
        int highestGainField = 0;
        double threshold = 0;

        /**
         * Find the attribute with the highest overall information gain
         */
        for (int i = 0; i < fields; i++) {
            final int fieldType = metadataProvider.getAttributeType(i);
            double subsetGain = setEntropy.getEntropy();

            if (fieldType == IAttributeMetadataProvider.CATEGORICAL) {
                for (double fieldValue : nameProvider.getAttributeValues(i)) {
                    Entropy fieldEntropy = new Entropy(set, i, fieldValue);
                    subsetGain -= fieldEntropy.getInformationGain(setSize);
                }
            } else if (fieldType == IAttributeMetadataProvider.CONTINUOUS) {
                threshold = findLowestThreshold(set, i);
                Entropy.EntropySet setSplit = Entropy.splitAtThreshold(set, i, threshold);
                subsetGain -= setSplit.left.getInformationGain(setSize);
                subsetGain -= setSplit.right.getInformationGain(setSize);
            }

            if (subsetGain > highestGain) {
                highestGain = subsetGain;
                highestGainField = i;
            }
        }

        final DecisionNode node = new DecisionNode(highestGainField);

        /**
         * Then we expand the values for the attribute with the lowest entropy/highest information gain
         */
        final int attributeType = metadataProvider.getAttributeType(highestGainField);
        if (attributeType == IAttributeMetadataProvider.CATEGORICAL) {
            for (final double name : nameProvider.getAttributeValues(highestGainField)) {
                final List<Observation> subset = filterSet(set, highestGainField, name);
                final Entropy trueFalseSubsetCount = new Entropy(subset);

                if (trueFalseSubsetCount.getEntropy() == 0) {
                    node.addChild(new LeafNode(name, subset.get(0).createInstance()));
                } else {
                    node.addChild(new AttributeNode(name, split(subset)));
                }
            }
        } else if (attributeType == IAttributeMetadataProvider.CONTINUOUS) {
            final List<Observation> subsetA = new LinkedList<>();
            final List<Observation> subsetB = new LinkedList<>();

            // Split set at threshold
            for (final Observation observation : set) {
                (observation.getObservationValue(highestGainField) <= threshold ? subsetA : subsetB).add(observation);
            }

            if (new Entropy(subsetA).getEntropy() == 0) {
                node.addChild(new AlphaLeafNode(threshold, subsetA.get(0).createInstance()));
            } else {
                node.addChild(new AlphaAttributeNode(threshold, split(subsetA)));
            }

            if (new Entropy(subsetB).getEntropy() == 0) {
                node.addChild(new BetaLeafNode(threshold, subsetB.get(0).createInstance()));
            } else {
                node.addChild(new BetaAttributeNode(threshold, split(subsetB)));
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
    private double findLowestThreshold(final List<Observation> set, final int index) {
        double lowestEntropy = 1;
        double lowestThreshold = 0;

        for (final Observation observation : set) {
            final double threshold = observation.getObservationValue(index);
            final double subsetEntropy = Entropy.findEntropyAtThreshold(set, index, threshold);

            if (lowestEntropy > subsetEntropy) {
                lowestThreshold = threshold;
                lowestEntropy = subsetEntropy;
            }
        }

        return lowestThreshold;
    }

    /**
     * Gets a subset out of parent set where a condition is true in the parent set.
     * @param set Parent set.
     * @param field Attribute index that will be compared against the condition.
     * @param condition The value that has to be equals to in the attribute.
     * @return Subset of set.
     */
    private List<Observation> filterSet(final List<Observation> set, final int field, final double condition) {
        return set.stream()
                .filter(observation -> observation.getObservationValue(field) == condition)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
