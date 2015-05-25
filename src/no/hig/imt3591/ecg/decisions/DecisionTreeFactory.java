package no.hig.imt3591.ecg.decisions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import no.hig.imt3591.id3.DecisionTree;
import no.hig.imt3591.id3.Observation;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Factory class that creates the decision tree based on a data set in set.json.
 */
public class DecisionTreeFactory {
    private static final String fileName = "set.json";

    /**
     * @return The decision trained using the data set from set.json.
     * @throws IOException Unable to load the file.
     */
    public static DecisionTree<SensorObservation> buildTree() throws IOException {
        final List<Observation<SensorObservation>> trainingSet = new LinkedList<>();

        for (final LearningObservation observation : readDataSet()) {
            trainingSet.add(new Observation<>(observation.getOutputType(), observation));
        }

        return new DecisionTree<>(SensorObservation.class, trainingSet);
    }

    private static LearningObservation[] readDataSet() throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return new Gson().fromJson(br, LearningObservation[].class);
        }
    }

    /**
     * Adds a learning observation to the data set.
     * @param observation The learning observation.
     * @throws IOException Unable to write to file.
     */
    public static void addLearningObservation(final LearningObservation observation) throws IOException {
        LearningObservation[] observations = readDataSet();
        LearningObservation[] allObservations = new LearningObservation[observations.length + 1];
        System.arraycopy(observations, 0, allObservations, 0, observations.length);
        allObservations[allObservations.length - 1] = observation;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (JsonWriter writer = new JsonWriter(bw)) {
                writer.setIndent("    ");
                gson.toJson(allObservations, LearningObservation[].class, writer);
            }
        }
    }
}
