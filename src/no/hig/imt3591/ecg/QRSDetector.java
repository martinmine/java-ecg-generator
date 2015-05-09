package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Detects QRS Points from the real time input.
 * It stores X amount of observations (numObservations) which is being analyzed on every X added.
 * The analyzeInput function Looks for the Q, R, and S points within the data set (voltages)
 * After every numObservations this class can give you both the frequency and the timestamp for each of these
 * points.
 */
public class QRSDetector {

    private List<Point.Double> observations;

    private QuickSort quickSort;
    private SimulatedAnnealing simulatedAnnealing;

    private double[] voltage;
    private double[] timestamp;
    private int maxObservations;
    private boolean isQuickSort;

    public static final int Q = 0;
    public static final int R = 1;
    public static final int S = 2;

    private void analyzeInput() {

        if (isQuickSort) {

            // Finds the biggest voltage out of the observations
            Point.Double point = quickSort.getMaximum(observations);

            // Update R point (timestamp, voltage):
            timestamp[R] = point.getX();
            voltage[R] = point.getY();

        } else {
            voltage[R] = simulatedAnnealing.search();
        }

        // TODO (NOTE)
        // The code below is for finding Q and S.

        // Difference in R position (close to observable start or end points)
        //double diff = (this.maxObservations - index[R]);

        // Finding Q - if R point is not the very first point (-offset) in the batch.
        // This means that the Q point was in the previous analyze.
        // These checks prevents outOfBounds exceptions.

        /*
        if (diff < (this.maxObservations - offset)) {

            // Find the lowest point before the topPeak (R):
            // Most probably the Q value.
            for (int j = 1; j < index[R]; j++) {
                if (voltages.get(j) < temp[Q]) {
                    temp[Q] = voltages.get(j);
                    index[Q] = j;
                }
            }

            // Update Q point (timestamp, voltage):
            timestamp[Q] = timestamps.get(index[Q]);
            voltage[Q] = temp[Q];
        }
        */

        // Finding S - Same case as finding Q, but opposite. As long as the R
        // point isn't at the end of this batch. Means S would be in the next batch.
        // These checks prevents outOfBounds exceptions.

        /*
        if (diff > offset) {

            // Update current lowest point after R peak.
            temp[S] = voltages.get(index[R]);

            // Find lowest point after R point.
            for (int k = (index[R] + 1); k < this.maxObservations; k++) {
                if (voltages.get(k) < temp[S]) {
                    temp[S] = voltages.get(k);
                    index[S] = k;
                }
            }

            // Update S point (timestamp, voltage):
            timestamp[S] = timestamps.get(index[S]);
            voltage[S] = temp[S];
        }
        */

        //voltage[R] = simulatedAnnealing.search();
        //System.out.println("hill climbing: " + voltage[R]);

    }

    public QRSDetector(int num, boolean isQuickSort) {
        this.observations = new ArrayList<>();

        this.isQuickSort = isQuickSort;
        this.maxObservations = num;

        quickSort = new QuickSort();
        simulatedAnnealing = new SimulatedAnnealing(this.observations, 1.0d, 0.99d);

        voltage = new double[3];
        timestamp = new double[3];
    }

    public void add(double voltage, double time) {

        // New sample, clears old.
        if (observations.size() == maxObservations) {
            observations.clear();
        }

        // Add new values
        observations.add(new Point.Double(time, voltage));

        // Got enough data to analyze:
        if (observations.size() == maxObservations) {
            analyzeInput();
        }
    }

    public double getVoltage(int type) {
        return voltage[type];
    }

    public double getTimestamp(int type) {
        return timestamp[type];
    }
}
