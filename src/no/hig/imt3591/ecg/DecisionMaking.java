package no.hig.imt3591.ecg;

import no.hig.imt3591.id3.DecisionTree;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 17.04.2015.
 */
public class DecisionMaking {
    private static DecisionTree ecgTree;
    private static DecisionTree pulseStressTree;
    private static DecisionTree oxygenSaturationTree;
    private static DecisionTree skinConductanceTree;

    public static void initialize() {
        // Train the trees
    }

    public static void onEnter(double ecg, double pulse, double pulseChange, double oxygen, double oxygenChange, double skinChange) {
        double[] set = new double[] {ecg, pulse, pulseChange, oxygen, oxygenChange, skinChange};

        //boolean ecgTriggered = ecgTree.search(set);
    }

}
