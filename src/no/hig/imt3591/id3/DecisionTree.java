package no.hig.imt3591.id3;

import no.hig.imt3591.id3.annotations.Attribute;
import no.hig.imt3591.id3.nodes.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for a decision tree, used to find or (somewhat) predict the outcome
 * of an event by creating the decision tree using a data set to build (train) it.
 */
public class DecisionTree<T> {
    private final DecisionNode<T> root;
    private final List<Field> attributes;

    /**
     * Creates a new decision tree using the ID3 algorithm.
     * @param observationType The observation data type.
     * @param set The data-set to train (build) the decision tree. This has to be an array
     *            where the last integer in the value is true/false.
     */
    public DecisionTree(final Class<T> observationType, final List<Observation<T>> set) {
        this.attributes = new LinkedList<>();
        for (Field f : observationType.getDeclaredFields()) {
            if (f.isAnnotationPresent(Attribute.class)) {
                this.attributes.add(f);
            }
        }
        this.root = split(set);
    }

    /**
     * Performs a search through the tree and finds an.
     * @param tuple The data that will be tested against the tree.
     * @return The output value of the data-set.
     */
    public ITreeResult search(final Observation<T> tuple) {
        return root.search(tuple);
    }

    /**
     * Splits the tree using the ID3 algorithm. This algorithm has been modified so it
     * also operates on continuous values.
     * See http://en.wikipedia.org/wiki/ID3_algorithm
     * @param set Current working subset
     * @return New node in the tree with its children
     */
    private DecisionNode<T> split(final List<Observation<T>> set) {
        final Entropy<T> setEntropy = new Entropy<>(set);
        final int setSize = set.size();

        double highestGain = 0;
        Field highestGainField = null;
        double threshold = 0;

        /**
         * Find the attribute with the highest overall information gain
         */
        for (Field field : this.attributes) {
            Attribute attribute = field.getAnnotation(Attribute.class);
            double subsetGain = setEntropy.getEntropy();

            if (attribute.type() == AttributeType.CATEGORICAL) {
                for (double fieldValue : attribute.outputValues()) {
                    Entropy<T> fieldEntropy = new Entropy<>(set, field, fieldValue);
                    subsetGain -= fieldEntropy.getInformationGain(setSize);
                }
            } else if (attribute.type() == AttributeType.CONTINUOUS) {
                threshold = findLowestThreshold(set, field);
                Entropy.EntropySet setSplit = Entropy.splitAtThreshold(set, field, threshold);
                subsetGain -= setSplit.left.getInformationGain(setSize);
                subsetGain -= setSplit.right.getInformationGain(setSize);
            }

            if (subsetGain > highestGain) {
                highestGain = subsetGain;
                highestGainField = field;
            }
        }

        if (highestGainField == null) {
            throw new RuntimeException("Something went severely wrong, check your data set");
        }

        final Attribute highestGainFieldAttribute = highestGainField.getAnnotation(Attribute.class);
        final DecisionNode<T> node = new DecisionNode<>(highestGainField);

        /**
         * Then we expand the values for the attribute with the lowest entropy/highest information gain
         */
        if (highestGainFieldAttribute.type() == AttributeType.CATEGORICAL) {
            for (final double name : highestGainFieldAttribute.outputValues()) {
                final List<Observation<T>> subset = filterSet(set, highestGainField, name);
                final Entropy<T> trueFalseSubsetCount = new Entropy<>(subset);

                if (trueFalseSubsetCount.getEntropy() == 0) {
                    node.addChild(new LeafNode(name, subset.get(0).createInstance()));
                } else {
                    node.addChild(new AttributeNode(name, split(subset)));
                }
            }
        } else if (highestGainFieldAttribute.type() == AttributeType.CONTINUOUS) {
            final List<Observation<T>> subsetA = new LinkedList<>();
            final List<Observation<T>> subsetB = new LinkedList<>();

            // Split set at threshold
            for (final Observation<T> observation : set) {
                (observation.getObservationValue(highestGainField) <= threshold ? subsetA : subsetB).add(observation);
            }

            if (new Entropy<>(subsetA).getEntropy() == 0) {
                node.addChild(new AlphaLeafNode(threshold, subsetA.get(0).createInstance()));
            } else {
                node.addChild(new AlphaAttributeNode(threshold, split(subsetA)));
            }

            if (new Entropy<>(subsetB).getEntropy() == 0) {
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
     * @param field The attribute that we want to find a threshold for.
     * @return The threshold value that gives the most information gain.
     */
    private double findLowestThreshold(final List<Observation<T>> set, final Field field) {
        double lowestEntropy = 1;
        double lowestThreshold = 0;

        for (final Observation observation : set) {
            final double threshold = observation.getObservationValue(field);
            final double subsetEntropy = Entropy.findEntropyAtThreshold(set, field, threshold);

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
    private List<Observation<T>> filterSet(final List<Observation<T>> set, final Field field, final double condition) {
        return set.stream()
                .filter(observation -> observation.getObservationValue(field) == condition)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
