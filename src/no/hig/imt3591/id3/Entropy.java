package no.hig.imt3591.id3;

import java.util.List;

/**
 * Entropy helper class for a set.
 */
public class Entropy {
    private int positives;
    private int negatives;

    /**
     * Finds entropy for a set.
     * @param set Set to find attribute for.
     * @param comparator The value in the set that determines true/false.
     * @param comparatorIndex Index of the comparator.
     */
    public Entropy (List<Double[]> set, double comparator, int comparatorIndex) {
        for (Double[] observation : set) {
            if (observation[comparatorIndex].equals(comparator)) {
                positives++;
            } else {
                negatives++;
            }
        }
    }

    /**
     * Finds entropy on a subset in a parent set where a condition has to be met on an attribute.
     * @param set The parent set.
     * @param attributes The attribute that a condition has to be met on,
     * @param value The value that has to be equal on the test attribute.
     * @param comparator The value in the set that determines true/false.
     * @param comparatorIndex Index of the comparator.
     */
    public Entropy (List<Double[]> set, int attributes, double value, double comparator, int comparatorIndex) {
        set.stream().filter(observation -> observation[attributes].equals(value)).forEach(observation -> {
            if (observation[comparatorIndex].equals(comparator)) {
                positives++;
            } else {
                negatives++;
            }
        });
    }

    public static class EntropySet {
        Entropy left;
        Entropy right;

        private EntropySet() {
            this.left = new Entropy();
            this.right = new Entropy();
        }

        public double getAverage() {
            return (left.getEntropy() + right.getEntropy()) / 2;
        }
    }

    public static EntropySet filterByThreshold(List<Double[]> set, int attributeIndex, double comparator, int comparatorIndex, double threshold) {

        EntropySet entropy = new EntropySet();

        for (Double[] observation : set) {
            Entropy comparing = observation[attributeIndex] <= threshold ? entropy.left : entropy.right;

            if (observation[comparatorIndex].equals(comparator)) {
                comparing.positives++;
            } else {
                comparing.negatives++;
            }
        }

        return entropy;
    }

    private Entropy() {
    }

    /**
     * Gets the entropy value, or the amount of bits that is required to represent an entity.
     * @return Amount of bits required to represent an attribute, eg. 0 means the set is pure, 1 means there is high uncertainty.
     */
    public double getEntropy() {
        if (negatives == 0 || positives == 0) {
            return 0;
        }

        return - (positives / (double)(positives + negatives)) * (Math.log(positives / (double)(positives + negatives)) / Math.log(2))
                - (negatives / (double)(positives + negatives)) * (Math.log(negatives / (double)(positives + negatives)) / Math.log(2));
    }

    public double getEntropySize() {
        return (double) positives + negatives;
    }
}
