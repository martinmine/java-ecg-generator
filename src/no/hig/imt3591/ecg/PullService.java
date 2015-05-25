package no.hig.imt3591.ecg;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Service that will pull for an ecg reading at a given interval/frequency.
 */
public class PullService {
    private final Timer timer;
    private final ComplexDetector complexDetector;

    /**
     * Creates a new pull service that will immediately start pulling for an ECG signal.
     * @param ecgProvider The ECG provider that provides the signal and the pulse.
     */
    public PullService(final EcgProvider ecgProvider) {
        this.timer = new Timer();

        this.complexDetector = new ComplexDetector(
                ecgProvider.getObservationLimit(),
                ecgProvider.getPeakDetectionMethod(),
                ecgProvider.getSATemperature(),
                ecgProvider.getSACoolDownRate(),
                ecgProvider.getSATemperatureLimit());

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
                public void run () {
                    complexDetector.onSignalReceived(ecgProvider);
                    StressIndicator.getInstance().onSignalReceived(
                        ecgProvider.getSkinResistance(),
                        ecgProvider.getOxygenSaturation(),
                        ecgProvider.getPulse());
                }
            }, 0, ecgProvider.getSamplingFrequency());
    }

    /**
     * Stops the pull service and stores the current states.
     */
    public void stop() {
        this.timer.cancel();
        this.timer.purge();

        StressIndicator.getInstance().onTearDown();
    }
}
