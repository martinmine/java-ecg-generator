package no.hig.imt3591.ecg.decisions.outcomes;

import no.hig.imt3591.id3.ITreeResult;
import og.acm.ecg.GraphPanel;

/**
 * Action indicating that the user should reduce the speed of the car.
 */
public class ReduceSpeedOutput implements ITreeResult {
    @Override
    public void invoke() {
        GraphPanel.getOutputReference().setOutput("Warning: User may be experiencing a cardiac event");
    }
}
