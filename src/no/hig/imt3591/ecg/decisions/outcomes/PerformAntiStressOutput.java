package no.hig.imt3591.ecg.decisions.outcomes;

import no.hig.imt3591.id3.ITreeResult;
import og.acm.ecg.GraphPanel;

/**
 * Action indicating that the user is stressed.
 */
public class PerformAntiStressOutput implements ITreeResult {
    @Override
    public void invoke() {
        GraphPanel.getOutputReference().setOutput("User stressed");
    }
}
