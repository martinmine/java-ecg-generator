package no.hig.imt3591.ecg.decisions;

import no.hig.imt3591.ecg.decisions.outcomes.IgnoreActionOutput;
import no.hig.imt3591.ecg.decisions.outcomes.PerformAntiStressOutput;
import no.hig.imt3591.ecg.decisions.outcomes.ReduceSpeedOutput;
import no.hig.imt3591.id3.ITreeResult;

/**
 * Created by marti_000 on 10.05.2015.
 */
public class LearningObservation extends SensorObservation {
    private String result;

    public LearningObservation(double cardiacEvent, double pulse, double pulseChange, double oxygen, double oxygenChange, double skin, double skinChange) {
        super(cardiacEvent, pulse, pulseChange, oxygen, oxygenChange, skin, skinChange);
    }

    public LearningObservation(double cardiacEvent, double pulse, double pulseChange, double oxygen, double oxygenChange, double skin, double skinChange, String result) {
        super(cardiacEvent, pulse, pulseChange, oxygen, oxygenChange, skin, skinChange);
        this.result = result;
    }

    public Class<? extends ITreeResult> getOutputType() {
        switch (result) {
            case "stressed":
                return PerformAntiStressOutput.class;
            case "heartProblem":
                return ReduceSpeedOutput.class;
            case "nothing":
                return IgnoreActionOutput.class;
            default:
                throw new RuntimeException(String.format("%s is an unknown result type", result));
        }
    }
}
