package no.hig.imt3591.ecg;

/**
 * Created by Martin on 17.04.2015.
 */
public class StressIndicator {
    private static final StressIndicator INSTANCE = new StressIndicator();

    public static StressIndicator getInstance() {
        return INSTANCE;
    }

    private StressIndicator() {
        // Read data
    }

    public void onSignalReceived(double skinResistance, double oxygenSaturation, double pulse) {

    }
}
