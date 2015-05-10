package no.hig.imt3591.ecg.decisions.outcomes;

import no.hig.imt3591.id3.ITreeResult;

/**
 * Created by marti_000 on 10.05.2015.
 */
public class PerformAntiStressOutput implements ITreeResult {
    @Override
    public void invoke() {
        System.out.println("calming down the user by reducing temperature, turning down volume or whatever");
    }
}
