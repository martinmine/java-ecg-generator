package no.hig.imt3591.ecg;

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
    private List<Double> voltages;
    private List<Double> timestamps;
    private double[] voltage;
    private double[] timestamp;
    private int numObservations;

    public static final int Q = 0;
    public static final int R = 1;
    public static final int S = 2;

    private void analyzeInput() {

        // Top point of the observable points.
        int index[] = new int[3];

        // Identify where the peak is out of the observables.
        double temp[] = new double[3];

        // R offset. If R peak is > 3 measures away from start or end of numObservations.
        int offset = 1;

        // Initialize help variables.
        temp[Q] = temp[R] = temp[S] = voltages.get(0);
        index[Q] = index[R] = index[S] = 0;

        // Finds the biggest voltage out of the observations
        // Most probably the R peak - depending on numberObservations:
        for (int i=1; i < numObservations; i++) {
            if (voltages.get(i) > temp[R]) {
                temp[R] = voltages.get(i);
                index[R] = i;
            }
        }

        // Update R point (timestamp, voltage):
        timestamp[R] = timestamps.get(index[R]);
        voltage[R] = temp[R];

        // Difference in R position (close to observable start or end points)
        double diff = (numObservations - index[R]);

        // Finding Q - if R point is not the very first point (-offset) in the batch.
        // This means that the Q point was in the previous analyze.
        // These checks prevents outOfBounds exceptions.
        if (diff < (numObservations - offset)) {

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

        // Finding S - Same case as finding Q, but opposite. As long as the R
        // point isn't at the end of this batch. Means S would be in the next batch.
        // These checks prevents outOfBounds exceptions.
        if (diff > offset) {

            // Update current lowest point after R peak.
            temp[S] = voltages.get(index[R]);

            // Find lowest point after R point.
            for (int k = (index[R] + 1); k < numObservations; k++) {
                if (voltages.get(k) < temp[S]) {
                    temp[S] = voltages.get(k);
                    index[S] = k;
                }
            }

            // Update S point (timestamp, voltage):
            timestamp[S] = timestamps.get(index[S]);
            voltage[S] = temp[S];
        }

    }

    public QRSDetector(int num) {
        voltages = new ArrayList<>();
        timestamps = new ArrayList<>();
        numObservations = num;
        voltage = new double[3];
        timestamp = new double[3];
    }

    public void add(double voltage, double time) {

        // New sample, clears old.
        if (voltages.size() == numObservations) {
            voltages.clear();
            timestamps.clear();
        }

        // Add new values
        timestamps.add(time);
        voltages.add(voltage);

        // Got enough data to analyze:
        if (voltages.size() == numObservations) {
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
