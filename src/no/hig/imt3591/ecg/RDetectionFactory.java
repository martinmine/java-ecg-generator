package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.List;

public class RDetectionFactory {

    public static IRDetection create(List<Point.Double> list, boolean isQuickSort, double temperature, double coolingRate, double temperatureLimit) {
        return (isQuickSort)? new QuickSort(list): new SimulatedAnnealing(list, temperature, coolingRate, temperatureLimit);
    }

}
