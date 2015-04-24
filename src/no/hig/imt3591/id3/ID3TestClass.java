package no.hig.imt3591.id3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ID3TestClass {
    private static List<Double[]> observations = new LinkedList<>();

    public static void main(String[] args) {
        observations.add(new Double[]{0d, 0d, 0d, 10d, 0d}); //D1
        observations.add(new Double[]{0d, 0d, 1d, 7d, 0d});  //D2
        observations.add(new Double[]{1d, 0d, 0d, 6d, 1d});  //D3
        observations.add(new Double[]{2d, 0d, 0d, 4d, 1d});  //D4
        observations.add(new Double[]{2d, 1d, 0d, 9d, 1d});  //D5
        observations.add(new Double[]{2d, 1d, 1d, 8d, 0d});  //D6
        observations.add(new Double[]{1d, 1d, 1d, 2d, 1d});  //D7
        observations.add(new Double[]{0d, 0d, 0d, 10d, 0d}); //D8
        observations.add(new Double[]{0d, 1d, 0d, 5d, 1d});  //D9
        observations.add(new Double[]{2d, 1d, 0d, 5d, 1d});  //D10
        observations.add(new Double[]{0d, 1d, 1d, 5d, 1d});  //D11
        observations.add(new Double[]{1d, 0d, 1d, 5d, 1d});  //D12
        observations.add(new Double[]{1d, 1d, 0d, 5d, 1d});  //D13
        observations.add(new Double[]{2d, 0d, 1d, 10d, 0d}); //D14

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

        DecisionTree tree = new DecisionTree(observations, 4, 1, nameProvider, metadataProvider);

        boolean failed = false;
        for (Double[] set : observations) {
            boolean result = tree.search(new double[]{set[0], set[1], set[2], set[4]});
            boolean outputResult = (set[3] == 1);
            if (result != outputResult) {
                failed = true;
                System.out.println("Test failed on set " + Arrays.toString(set));
            } else {
                System.out.println("Test passed: " + Arrays.toString(set));
            }
        }

        if (failed) {
            System.out.println("Tests failed");
        } else {
            System.out.println("All tests passed!");
        }
    }
}
