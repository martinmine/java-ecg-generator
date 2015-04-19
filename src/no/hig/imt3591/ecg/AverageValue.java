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

    public double getValue() {
        return currentTotal / count;
    }

    public int getCount() {
        return count;
    }

    public void putValue(double value) {
        currentTotal += value;
        count++;
    }
}
