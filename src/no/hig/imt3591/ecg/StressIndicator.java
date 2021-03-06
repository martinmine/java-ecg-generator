package no.hig.imt3591.ecg;

import java.util.logging.Logger;

/**
 * Attempts to detect stress levels based on comparing current values with normal
 * behaviour based on average values.
 */
public class StressIndicator {
    private static final StressIndicator INSTANCE = new StressIndicator();
    private static final Logger LOGGER = Logger.getLogger(StressIndicator.class.getSimpleName());

    private final VariabilityIdentifier skinConductance;
    private final VariabilityIdentifier oxygenSaturation;
    private final VariabilityIdentifier pulse;

    public static StressIndicator getInstance() {
        return INSTANCE;
    }

    private StressIndicator() {
        this.pulse = new VariabilityIdentifier(60, 0.2, 20, 0.8);
        this.skinConductance = new VariabilityIdentifier(60, 0.2, 20, 0.8);
        this.oxygenSaturation = new VariabilityIdentifier(20, 0.2, 20, 0.8);
    }

    /**
     * Processes a received signal.
     * @param skinResistanceValue Skin conductance value.
     * @param oxygenSaturationValue Oxygen saturation value.
     * @param pulseValue Current pulse.
     */
    public void onSignalReceived(final double skinResistanceValue, final double oxygenSaturationValue, final double pulseValue) {
        final double skinVariability = skinConductance.addValue(skinResistanceValue);
        final double oxygenVariability = oxygenSaturation.addValue(oxygenSaturationValue);
        final double pulseVariability = pulse.addValue(pulseValue);

        LOGGER.info("Skin resistance: " + skinResistanceValue + " oxygenSaturation: " + oxygenSaturationValue + " pulse: " + pulseValue);
        LOGGER.info("Variability: Skin: " + skinVariability + ", Oxygen: " + oxygenVariability + ", Pulse: " + pulseVariability);
        DecisionMaking.getInstance().onEnter(0, pulseValue, pulseVariability, oxygenSaturationValue, oxygenVariability, skinResistanceValue, skinVariability);
    }

    /**
     * Saves the average values for the different average values.
     */
    public void onTearDown() {
        skinConductance.endSession();
        oxygenSaturation.endSession();
        pulse.endSession();
    }
}
