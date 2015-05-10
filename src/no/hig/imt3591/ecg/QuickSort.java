package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.List;

public class QuickSort {

    private List<Point.Double> observations;

    private void swap(int i, int j) {
        Point.Double temp = observations.get(i);
        observations.set(i, observations.get(j));
        observations.set(j, temp);
    }

    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;

        int pivotIndex = lowerIndex + (higherIndex - lowerIndex) / 2;
        Point.Double pivot = observations.get(pivotIndex);

        while (i <= j) {


            while (observations.get(i).getY() < pivot.getY()) {
                i++;
            }

            while (observations.get(j).getY() > pivot.getY()) {
                j--;
            }

            if (i <= j) {
                swap(i++, j--);
            }

        }

        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    public Point.Double getMaximum(List<Point.Double> list) {
        this.observations = list;
        quickSort(0, observations.size() - 1);
        return observations.get(observations.size() - 1);
    }
}
