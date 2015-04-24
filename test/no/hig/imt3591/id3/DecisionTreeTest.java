package no.hig.imt3591.id3;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class DecisionTreeTest {
    private static List<Observation> observations = new LinkedList<>();

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
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 0d, 10d}));
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 1d, 7d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 0d, 0d, 6d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 0d, 0d, 4d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 1d, 0d, 9d}));
        observations.add(new Observation(FalseOutputValue.class, new Double[]{2d, 1d, 1d, 8d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 1d, 1d, 2d}));
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 0d, 10d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{0d, 1d, 0d, 5d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 1d, 0d, 5d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{0d, 1d, 1d, 5d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 0d, 1d, 5d}));
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 1d, 0d, 5d}));
        observations.add(new Observation(FalseOutputValue.class, new Double[]{2d, 0d, 1d, 10d}));

        IAttributeNameProvider nameProvider = i -> {
            switch (i) {
                case 0:
                    return new Double[]{0d, 1d, 2d};
                case 1:
                    return new Double[]{0d, 1d};
                case 2:
                    return new Double[]{0d, 1d};
                default:
                    return null;
            }
        };

        IAttributeMetadataProvider metadataProvider = attributeIndex -> {
            switch (attributeIndex) {
                case 0:
                case 1:
                case 2:
                default:
                    return IAttributeMetadataProvider.CATEGORICAL;
                case 3:
                    return IAttributeMetadataProvider.CONTINUOUS;
            }
        };

        DecisionTree tree = new DecisionTree(observations, 4, nameProvider, metadataProvider);

        for (Observation observation : observations) {
            ITreeResult result = tree.search(observation);
            assertEquals(observation.getResultType(), result.getClass());
        }
    }
}