package no.hig.imt3591.ecg;

import java.awt.*;
import java.awt.geom.Point2D;
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
    private IRDetection irDetection;
    private Point.Double[] point;

    private int maxObservations;

    public static final int Q = 0;
    public static final int R = 1;
    public static final int S = 2;

    /**
     * Analyzing the input, finding the R peak depending on the chosen method which is
     * handled by the IRDetection interface.
     */
    private void analyzeInput() {
        point[R] = irDetection.getMaximum();
    }

    public QRSDetector(int num, boolean isQuickSort, double temperature, double coolingRate, double temperatureLimit) {
        this.observations = new ArrayList<>();
        this.irDetection = RDetectionFactory.create(this.observations, isQuickSort, temperature, coolingRate, temperatureLimit);
        this.maxObservations = num;

        this.point = new Point.Double[3];
        this.point[Q] = new Point2D.Double(0,0);
        this.point[R] = new Point2D.Double(0,0);
        this.point[S] = new Point2D.Double(0,0);
    }

    /**
     * Handel each observation iterations. As the inout is continuous.
     * @param voltage Double voltage Y axis.
     * @param time Double timestamp X axis.
     */
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
        return point[type].getY();
    }

    public double getTimestamp(int type) {
        return point[type].getX();
    }
}
