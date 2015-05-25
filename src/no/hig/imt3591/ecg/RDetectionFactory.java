package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.List;

public class RDetectionFactory {

    public static IRDetection create(List<Point.Double> list, boolean isQuickSort, double temperature, double coolingRate, double temperatureLimit) {
        if (isQuickSort) {
            return new QuickSort(list);
        } else {
            return new SimulatedAnnealing(list, temperature, coolingRate, temperatureLimit);
        }
    }
}
