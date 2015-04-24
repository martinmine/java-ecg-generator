package no.hig.imt3591.id3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ID3TestClass {
    private static List<Integer[]> observations = new LinkedList<>();

    public static void main(String[] args) {
        observations.add(new Integer[]{0, 0, 0, 10, 0}); //D1
        observations.add(new Integer[]{0, 0, 1, 7, 0});  //D2
        observations.add(new Integer[]{1, 0, 0, 6, 1});  //D3
        observations.add(new Integer[]{2, 0, 0, 4, 1});  //D4
        observations.add(new Integer[]{2, 1, 0, 9, 1});  //D5
        observations.add(new Integer[]{2, 1, 1, 8, 0});  //D6
        observations.add(new Integer[]{1, 1, 1, 2, 1});  //D7
        observations.add(new Integer[]{0, 0, 0, 10, 0}); //D8
        observations.add(new Integer[]{0, 1, 0, 5, 1});  //D9
        observations.add(new Integer[]{2, 1, 0, 5, 1});  //D10
        observations.add(new Integer[]{0, 1, 1, 5, 1});  //D11
        observations.add(new Integer[]{1, 0, 1, 5, 1});  //D12
        observations.add(new Integer[]{1, 1, 0, 5, 1});  //D13
        observations.add(new Integer[]{2, 0, 1, 10, 0}); //D14

        IAttributeNameProvider nameProvider = i -> {
                switch (i) {
                    case 0:
                        return new Integer[]{0,1,2};
                    case 1:
                        return new Integer[]{0,1};
                    case 2:
                        return new Integer[]{0,1};
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
        for (Integer[] set : observations) {
            boolean result = tree.search(new int[]{set[0], set[1], set[2], set[4]});
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
