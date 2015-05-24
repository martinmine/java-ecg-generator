package no.hig.imt3591.id3;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class DecisionTreeTest {
    private static List<Observation<TennisObservation>> observations = new LinkedList<>();

    public static class TrueOutputValue implements ITreeResult {
        @Override
        public void invoke() {
        }
    }

    public static class FalseOutputValue implements ITreeResult {
        @Override
        public void invoke() {
        }
    }

    @Test
    public void testTree() {
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(0d, 0d, 0d, 10d)));
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(0d, 0d, 1d, 7d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(1d, 0d, 0d, 6d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(2d, 0d, 0d, 4d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(2d, 1d, 0d, 9d)));
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(2d, 1d, 1d, 8d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(1d, 1d, 1d, 2d)));
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(0d, 0d, 0d, 10d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(0d, 1d, 0d, 5d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(2d, 1d, 0d, 5d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(0d, 1d, 1d, 5d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(1d, 0d, 1d, 5d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(1d, 1d, 0d, 5d)));
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(2d, 0d, 1d, 10d)));

        DecisionTree<TennisObservation> tree = new DecisionTree<>(TennisObservation.class, observations);

        for (Observation<TennisObservation> observation : observations) {
            ITreeResult result = tree.search(observation);
            assertEquals(observation.getResultType(), result.getClass());
        }
    }

    @Test
    public void testBinary() {
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(0d, 0d, 0d, 2d)));
        observations.add(new Observation<>(FalseOutputValue.class, new TennisObservation(0d, 0d, 0d, 3d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(1d, 0d, 0d, 2d)));
        observations.add(new Observation<>(TrueOutputValue.class, new TennisObservation(2d, 0d, 0d, 3d)));

        DecisionTree<TennisObservation> tree = new DecisionTree<>(TennisObservation.class, observations);

        assertNotNull(tree);
    }
}