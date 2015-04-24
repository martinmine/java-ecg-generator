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
        for (final Observation observation : set) {
            addCount(observation.getResultType());
        }
    }

    /**
     * Finds entropy on a subset in a parent set where a condition has to be met on an attribute.
     * @param set The parent set.
     * @param attributes The attribute that a condition has to be met on,
     * @param value The value that has to be equal on the test attribute.
     */
    public Entropy (final List<Observation> set, final int attributes, final double value) {
        set.stream().filter(observation -> observation.getTuple()[attributes].equals(value)).forEach(observation -> {
            addCount(observation.getResultType());
        });
    }

    public static class EntropySet {
        final Entropy left;
        final Entropy right;

        private EntropySet() {
            this.left = new Entropy();
            this.right = new Entropy();
        }
    }

    public static EntropySet filterByThreshold(final List<Observation> set, final int attributeIndex, final double threshold) {
        final EntropySet entropy = new EntropySet();

        for (final Observation observation : set) {
            final Entropy comparing = observation.getTuple()[attributeIndex] <= threshold ? entropy.left : entropy.right;
            comparing.addCount(observation.getResultType());
        }

        return entropy;
    }

    private void addCount(final Class<? extends ITreeResult> resultType) {
        totalSetCount++;
        int occurrence = 1;

        if (count.containsKey(resultType)) {
            occurrence = count.get(resultType) + 1;
        }

        count.put(resultType, occurrence);
    }

    private Entropy() {
    }

    /**
     * Gets the entropy value, or the amount of bits that is required to represent an entity.
     * @return Amount of bits required to represent an attribute, eg. 0 means the set is pure, 1 means there is high uncertainty.
     */
    public double getEntropy() {
        double entropy = 0;
        if (count.size() == 1) {
            return entropy;
        }

        for (int typeCount : count.values()) {
            entropy -= (typeCount / getEntropySize()) * (Math.log(typeCount / getEntropySize())) / Math.log(2);
        }

        return entropy;
    }

    public double getEntropySize() {
        return totalSetCount;
    }
}
