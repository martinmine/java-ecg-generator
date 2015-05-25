package no.hig.imt3591.ecg.decisions.outcomes;

import no.hig.imt3591.id3.ITreeResult;
import og.acm.ecg.GraphPanel;

/**
 * Action that indicates no action should be performed.
 */
public class IgnoreActionOutput implements ITreeResult {
    @Override
    public void invoke() {
        GraphPanel.getOutputReference().setOutput("No action required");
    }
}
