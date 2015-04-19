package no.hig.imt3591.ecg;

import java.util.logging.Logger;

/**
 * Created by Martin on 17.04.2015.
 */
public class StressIndicator {
    private static final StressIndicator INSTANCE = new StressIndicator();
    private static final Logger LOGGER = Logger.getLogger(StressIndicator.class.getSimpleName());

    private VariabilityIdentifier skinConductance;
    private VariabilityIdentifier oxygenSaturation;
    private VariabilityIdentifier pulse;

    public static StressIndicator getInstance() {
        return INSTANCE;
    }

    private StressIndicator() {
        pulse = new VariabilityIdentifier(70, 0.2, 20, 0.8);
        skinConductance = new VariabilityIdentifier(60, 0.2, 20, 0.8);
        oxygenSaturation = new VariabilityIdentifier(20, 0.2, 20, 0.8);
    }

    public void onSignalReceived(double skinResistanceValue, double oxygenSaturationValue, double pulseValue) {
        LOGGER.info("Skin resistance: " + skinResistanceValue + " oxygenSaturation: " + oxygenSaturationValue + " pulse: " + pulseValue);

        double s = skinConductance.addValue(skinResistanceValue);
        double o = oxygenSaturation.addValue(oxygenSaturationValue);
        double p = pulse.addValue(pulseValue);

        LOGGER.info("Variability: Skin: " + s + ", Oxygen: " + o + ", Pulse: " + p);
    }

    public void onTearDown() {
        skinConductance.endSession();
        oxygenSaturation.endSession();
        pulse.endSession();
    }
}
