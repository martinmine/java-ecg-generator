package no.hig.imt3591.ecg;

import no.hig.imt3591.ecg.decisions.DecisionTreeFactory;
import no.hig.imt3591.ecg.decisions.LearningObservation;
import no.hig.imt3591.ecg.decisions.SensorObservation;
import no.hig.imt3591.id3.DecisionTree;
import no.hig.imt3591.id3.ITreeResult;
import no.hig.imt3591.id3.Observation;

import java.io.IOException;


/**
 * Creates decisions based on input data towards decision tree(s).
 */
public class DecisionMaking {
    private static final DecisionMaking INSTANCE = new DecisionMaking();
    public static DecisionMaking getInstance() {
        return INSTANCE;
    }

    private DecisionTree<SensorObservation> tree;
    private String sampleOutputAction;

    public DecisionMaking() {
        try {
            tree = DecisionTreeFactory.buildTree();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEnter(double ecg, double pulse, double pulseChange, double oxygen, double oxygenChange, double skinChange, double skin) {
        SensorObservation observation = new SensorObservation(ecg, pulse, pulseChange, oxygen, oxygenChange, skin, skinChange);

        ITreeResult result = tree.search(new Observation<>(null, observation));
        if (result != null) {
            result.invoke();
        } else {
            System.out.println("WARNING: NO RESULT");
        }

        if (sampleOutputAction != null) {
            try {
                DecisionTreeFactory.addLearningObservation(new LearningObservation(ecg, pulse, pulseChange, oxygen, oxygenChange, skin, skinChange, sampleOutputAction));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sampleOutputAction = null;
        }
    }

    public void queueLearningSample(final String outputAction) {
        this.sampleOutputAction = outputAction;
    }
}
