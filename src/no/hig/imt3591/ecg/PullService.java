package no.hig.imt3591.ecg;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Service that will pull for an ecg reading at a given interval/frequency.
 */
public class PullService {
    private Timer timer;
    private EcgProvider ecgProvider;
    private static final int SAMPLING_FREQUENCY = 10;
    private static final Logger LOGGER = Logger.getLogger(PullService.class.getSimpleName());

    /**
     * Creates a new pull service that will immediately start pulling for an ECG signal.
     * @param ecgProvider The ECG provider that provides the signal and the pulse.
     */
    public PullService(EcgProvider ecgProvider) {
        this.ecgProvider = ecgProvider;
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ComplexDetector.onSignalReceived(ecgProvider);
                StressIndicator.getInstance().onSignalReceived(
                        ecgProvider.getSkinResistance(),
                        ecgProvider.getOxygenSaturation(),
                        ecgProvider.getPulse());
            }
        }, 0, SAMPLING_FREQUENCY);
    }

    public void stop() {
        this.timer.cancel();
        this.timer.purge();
    }
}
