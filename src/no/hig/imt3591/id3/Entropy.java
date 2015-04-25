package no.hig.imt3591.id3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entropy helper class for a set.
 */
public class Entropy {
    private final Map<Class<? extends ITreeResult>, Integer> count = new HashMap<>();
    private int totalSetCount;

    /**
     * Finds entropy for a set.
     * @param set Set to find attribute for.
     */
    public Entropy (final List<Observation> set) {
        set.stream().forEach(o -> addCount(o.getResultType()));
    }

    /**
     * Finds entropy on a subset in a parent set where a condition has to be met on an attribute.
     * @param set The parent set.
     * @param attributes The attribute that a condition has to be met on,
     * @param value The value that has to be equal on the test attribute.
     */
    public Entropy (final List<Observation> set, final int attributes, final double value) {
        set.stream()
                .filter(observation -> observation.getObservationValue(attributes) == value)
                .forEach(observation -> addCount(observation.getResultType()));
    }

    private Entropy() {
    }

    public static class EntropySet {
        final Entropy left;
        final Entropy right;

        private EntropySet() {
            this.left = new Entropy();
            this.right = new Entropy();
        }
    }

    public static EntropySet splitAtThreshold(final List<Observation> set, final int attributeIndex, final double threshold) {
        final EntropySet entropySet = new EntropySet();

        for (final Observation observation : set) {
            (observation.getObservationValue(attributeIndex) <= threshold ? entropySet.left : entropySet.right)
                    .addCount(observation.getResultType());
        }

        return entropySet;
    }

    public static double findEntropyAtThreshold(final List<Observation> set, final int attributeIndex, final double threshold) {
        final Entropy.EntropySet entropy = Entropy.splitAtThreshold(set, attributeIndex, threshold);
        return (entropy.left.getEntropy() + entropy.right.getEntropy()) / 2;
    }

    private void addCount(final Class<? extends ITreeResult> resultType) {
        count.put(resultType, count.getOrDefault(resultType, 0) + 1);
        ++totalSetCount;
    }

    /**
     * Gets the entropy value, or the amount of bits that is required to represent an entity.
     * @return Amount of bits required to represent an attribute, eg. 0 means the set is pure, 1 means there is high uncertainty.
     */
    public double getEntropy() {
        double entropy = 0;

        for (int typeCount : count.values()) {
            entropy -= (typeCount / getEntropySize()) * (Math.log(typeCount / getEntropySize())) / Math.log(2);
        }

        return entropy;
    }

    private double getEntropySize() {
        return totalSetCount;
    }

    /**
     * Calculates information gain from one set,
     * @param superSetSize The size of the parent set.
     * @return Information gain.
     */
    public double getInformationGain(final double superSetSize) {
        return getEntropySize() / superSetSize * getEntropy();
    }
}
