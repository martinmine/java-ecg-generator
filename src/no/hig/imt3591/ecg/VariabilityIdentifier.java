package no.hig.imt3591.ecg;

import java.util.logging.Logger;

/**
 * Created by Martin on 17.04.2015.
 */
public class VariabilityIdentifier {
    private AverageValue globalAverage;
    private AverageValue currentAverage;
    private double saveThreshold;

    private static final Logger LOGGER = Logger.getLogger(VariabilityIdentifier.class.getSimpleName());

    private double minReadValues;
    private double deviationAmplifier;

    public VariabilityIdentifier(double initialValue, double saveThreshold, double minReadValues, double deviationAmplifier) {
        this.globalAverage = new AverageValue(initialValue, 1);
        this.currentAverage = new AverageValue();
        this.saveThreshold = saveThreshold;
        this.minReadValues = minReadValues;
        this.deviationAmplifier = deviationAmplifier;
    }

    public double addValue(double value) {
        try {
            if (currentAverage.getCount() < minReadValues || currentAverage.getValue() > globalAverage.getValue()) {
                return (value / globalAverage.getValue()) * deviationAmplifier;
            } else {
                return value / currentAverage.getValue();
            }
        } finally {
            currentAverage.putValue(value);
        }
    }

    public void endSession() {
        double difference = currentAverage.getValue() / globalAverage.getValue();

        if (difference > (1 - saveThreshold) && difference < (1 + saveThreshold)) {
            globalAverage.putValue(currentAverage.getValue());
            LOGGER.info("Changes saved with a difference of " + difference);
        } else {
            LOGGER.info("Changes discarded with a difference of " + difference);
        }
    }
}
