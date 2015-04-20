package no.hig.imt3591.ecg;

/**
 * Class for calculating the average of a set of values.
 */
public class AverageValue {
    private double currentTotal;
    private int count;

    public AverageValue(double currentTotal, int count) {
        this.currentTotal = currentTotal;
        this.count = count;
    }

    public AverageValue() {
    }

    /**
     * @return Current average.
     */
    public double getValue() {
        return currentTotal / count;
    }

    /**
     * @return Amount of values.
     */
    public int getCount() {
        return count;
    }

    /**
     * Adds a value to the set.
     * @param value New value.
     */
    public void putValue(double value) {
        currentTotal += value;
        count++;
    }
}
