package no.hig.imt3591.ecg.decisions;

import com.google.gson.Gson;
import no.hig.imt3591.id3.DecisionTree;
import no.hig.imt3591.id3.Observation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marti_000 on 10.05.2015.
 */
public class DecisionTreeFactory {
    public static DecisionTree<SensorObservation> buildTree() throws IOException {
        final Gson gson = new Gson();
        final List<Observation<SensorObservation>> trainingSet = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("set.json"))) {
            for (LearningObservation observation : gson.fromJson(br, LearningObservation[].class)) {
                trainingSet.add(new Observation<>(observation.getOutputType(), observation));
            }
        }

        return new DecisionTree<>(SensorObservation.class, trainingSet);
    }
}
