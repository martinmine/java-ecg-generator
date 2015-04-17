package no.hig.imt3591.ecg;

import java.util.logging.Logger;

/**
 * Created by Martin on 17.04.2015.
 */
public class ComplexDetector {
    private static final Logger LOGGER = Logger.getLogger(ComplexDetector.class.getSimpleName());

    public static void onSignalReceived(EcgProvider ecgProvider) {
        double voltage = ecgProvider.getVoltage();
        double pulse = ecgProvider.getPulse();

        LOGGER.info("v = " + voltage + " @" + pulse);
    }
}
