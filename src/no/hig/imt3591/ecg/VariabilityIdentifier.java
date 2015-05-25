package no.hig.imt3591.ecg;

import java.util.logging.Logger;

/**
 * Detects irregularities on data-inputs.
 */
public class VariabilityIdentifier {
    private final AverageValue globalAverage;
    private final AverageValue currentAverage;
    private final double saveThreshold;

    private static final Logger LOGGER = Logger.getLogger(VariabilityIdentifier.class.getSimpleName());

    private final double minReadValues;
    private final double deviationAmplifier;

    /**
     * Creates a new variability identifier and initializes it for usage.
     * @param initialValue The initial average value for the identifier.
     * @param saveThreshold Threshold value for when it should ignore values.
     * @param minReadValues The minimum amount of values it will compare against
     *                      global average before using current session.
     * @param deviationAmplifier Amplifier for when the value is different from normal values.
     */
    public VariabilityIdentifier(double initialValue, double saveThreshold,
                                 double minReadValues, double deviationAmplifier) {
        this.globalAverage = new AverageValue(initialValue, 1);
        this.currentAverage = new AverageValue();
        this.saveThreshold = saveThreshold;
        this.minReadValues = minReadValues;
        this.deviationAmplifier = deviationAmplifier;
    }

    /**
     * Adds one sensor value to the identifier.
     * @param value New input sensor reading value.
     * @return How much the value differs from other readings.
     */
    public double addValue(double value) {
        try {
            if (currentAverage.getCount() < minReadValues
                    || currentAverage.getValue() > globalAverage.getValue()) {
                return (value / globalAverage.getValue()) * deviationAmplifier;
            } else {
                return value / currentAverage.getValue();
            }
        } finally {
            currentAverage.putValue(value);
        }
    }

    /**
     * Ends a session and saves the values to the global average if possible.
     * This function is called eg. when the user is done with one car trip.
     */
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
