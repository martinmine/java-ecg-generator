package no.hig.imt3591.id3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ID3TestClass {
    private static List<Integer[]> observations = new LinkedList<>();

    public static void main(String[] args) {
        observations.add(new Integer[]{0, 0, 0, 0, 10}); //D1
        observations.add(new Integer[]{0, 0, 1, 0, 7});  //D2
        observations.add(new Integer[]{1, 0, 0, 1, 6});  //D3
        observations.add(new Integer[]{2, 0, 0, 1, 4});  //D4
        observations.add(new Integer[]{2, 1, 0, 1, 9});  //D5
        observations.add(new Integer[]{2, 1, 1, 0, 8});  //D6
        observations.add(new Integer[]{1, 1, 1, 1, 2});  //D7
        observations.add(new Integer[]{0, 0, 0, 0, 10}); //D8
        observations.add(new Integer[]{0, 1, 0, 1, 5});  //D9
        observations.add(new Integer[]{2, 1, 0, 1, 5});  //D10
        observations.add(new Integer[]{0, 1, 1, 1, 5});  //D11
        observations.add(new Integer[]{1, 0, 1, 1, 5});  //D12
        observations.add(new Integer[]{1, 1, 0, 1, 5});  //D13
        observations.add(new Integer[]{2, 0, 1, 0, 10}); //D14

        IAttributeNameProvider nameProvider = i -> {
                switch (i) {
                    case 0:
                        return new Integer[]{0,1,2};
                    case 1:
                        return new Integer[]{0,1};
                    case 2:
                    default:
                        return new Integer[]{0,1};
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
            if (result == (set[3] == 1)) {
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
