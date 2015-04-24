package no.hig.imt3591.id3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// TODO: Make this JUnit test instead
public class ID3TestClass {
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


    public static void main(String[] args) {
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 0d, 10d})); //D1
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 1d, 7d}));  //D2
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 0d, 0d, 6d}));  //D3
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 0d, 0d, 4d}));  //D4
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 1d, 0d, 9d}));  //D5
        observations.add(new Observation(FalseOutputValue.class, new Double[]{2d, 1d, 1d, 8d}));  //D6
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 1d, 1d, 2d}));  //D7
        observations.add(new Observation(FalseOutputValue.class, new Double[]{0d, 0d, 0d, 10d})); //D8
        observations.add(new Observation(TrueOutputValue.class, new Double[]{0d, 1d, 0d, 5d}));  //D9
        observations.add(new Observation(TrueOutputValue.class, new Double[]{2d, 1d, 0d, 5d}));  //D10
        observations.add(new Observation(TrueOutputValue.class, new Double[]{0d, 1d, 1d, 5d}));  //D11
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 0d, 1d, 5d}));  //D12
        observations.add(new Observation(TrueOutputValue.class, new Double[]{1d, 1d, 0d, 5d}));  //D13
        observations.add(new Observation(FalseOutputValue.class, new Double[]{2d, 0d, 1d, 10d})); //D14

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

        boolean failed = false;
        for (Observation observation : observations) {
            ITreeResult result = tree.search(observation);
            if (!result.getClass().equals(observation.getResultType())) {
                failed = true;
                System.out.println("Test failed on set " + Arrays.toString(observation.getTuple()));
            } else {
                System.out.println("Test passed: " + Arrays.toString(observation.getTuple()));
            }
        }

        if (failed) {
            System.out.println("Tests failed");
        } else {
            System.out.println("All tests passed!");
        }
    }
}
