package no.hig.imt3591.ecg;

import java.awt.*;
import java.util.logging.Logger;

/**
 * This class handles drawing the QRS complex and the data set being analyzed.
 *
 * TODO: Find a solution to fit bpm (RR interval)
 * Problem: The animation starts on an R peak. (middle fo a QRS complex)
 * Should be dynamically changed depending on sample frequency (SF) and number of measurements (NOM).
 * SF is a factor into this as some samples doesn't get detected which again plays a huge
 * role in how many measures we analyze at a time.
 * Current solution: Happens to looks good with 80 with a Sample frequency of 10.
 */
public class ComplexDetector {
    private static final Logger LOGGER = Logger.getLogger(ComplexDetector.class.getSimpleName());

    private static final int NUM_MEASUREMENTS = 80;

    private static QRSDetector qrsDetector = new QRSDetector(NUM_MEASUREMENTS, true);

    private static int i = 0;
    private static int startOfMeasurementsPositionX = 0;
    private static int initialZero = 0;

    public static void onSignalReceived(EcgProvider ecgProvider) {

        // Oval size for drawing Q, R, and S points.
        int ovalSize = 10;

        //double pulse = ecgProvider.getPulse();
        double voltage = ecgProvider.getVoltage();

        // Time and plotZoom for representing current x position:
        double time = ecgProvider.getTime();
        double plotZoom = ecgProvider.getPlotZoom();

        // Graphics and points for drawing:
        Graphics g = ecgProvider.getGraphGraphics();
        Point lastPoint = ecgProvider.getLastPoint();
        Point currentPoint = ecgProvider.getCurrentPoint();

        // For converting voltage into pixel positions - for drawing:
        int frameAmplitude = ecgProvider.getFrameAmplitude();
        double amplitude = ecgProvider.getAmplitude();

        // Initializing the Q, R, and S points for drawing:
        Point point[] = new Point[3];
        point[QRSDetector.Q] = new Point(0,0);
        point[QRSDetector.R] = new Point(0,0);
        point[QRSDetector.S] = new Point(0,0);

        double timestamps[] = new double[3];
        double voltages[] = new double[3];

        qrsDetector.add(voltage, time);

        // Draw each measures:
        if (i != 0  &&  (i % NUM_MEASUREMENTS) == 0) {

            // Retrieve Q point [timestamp, voltage] from observed measures.
            timestamps[QRSDetector.Q] = qrsDetector.getTimestamp(QRSDetector.Q);
            voltages[QRSDetector.Q] = qrsDetector.getVoltage(QRSDetector.Q);

            // Retrieve R point [timestamp, voltage] from observed measures.
            timestamps[QRSDetector.R] = qrsDetector.getTimestamp(QRSDetector.R);
            voltages[QRSDetector.R] = qrsDetector.getVoltage(QRSDetector.R);

            // Retrieve S point [timestamp, voltage] from observed measures.
            timestamps[QRSDetector.S] = qrsDetector.getTimestamp(QRSDetector.S);
            voltages[QRSDetector.S] = qrsDetector.getVoltage(QRSDetector.S);

            // Sets the X value based on their timestamps:
            point[QRSDetector.Q].x = (int)(timestamps[QRSDetector.Q] / plotZoom) - initialZero;
            point[QRSDetector.R].x = (int)(timestamps[QRSDetector.R] / plotZoom) - initialZero;
            point[QRSDetector.S].x = (int)(timestamps[QRSDetector.S] / plotZoom) - initialZero;

            // Sets the Y value based on their voltage:
            point[QRSDetector.Q].y = frameAmplitude - (int)(voltages[QRSDetector.Q] * (frameAmplitude / amplitude));
            point[QRSDetector.R].y = frameAmplitude - (int)(voltages[QRSDetector.R] * (frameAmplitude / amplitude));
            point[QRSDetector.S].y = frameAmplitude - (int)(voltages[QRSDetector.S] * (frameAmplitude / amplitude));


            // Handle drawing while all measurements is within the drawing panel
            // Else - the observed voltages is drawn in between a repaint.
            if (startOfMeasurementsPositionX < lastPoint.x) {

                g.setColor(Color.RED);
                g.drawLine(startOfMeasurementsPositionX, point[QRSDetector.R].y, currentPoint.x, point[QRSDetector.R].y);

            } else {

                // Reset x position based on timestamps after a wrap (starting at x = 0 again - for drawing);
                initialZero = (int)(time / plotZoom) - currentPoint.x;

                g.setColor(Color.RED);

                // Draw to the end of the drawing window (850 in width).
                g.drawLine(startOfMeasurementsPositionX, point[QRSDetector.R].y, 850, point[QRSDetector.R].y);

                // Continue the drawing on wrap from 0 to current x position.
                g.drawLine(0, point[QRSDetector.R].y, currentPoint.x, point[QRSDetector.R].y);
            }

            g.setColor(Color.BLUE);
            g.drawOval(point[QRSDetector.Q].x - (ovalSize/2), point[QRSDetector.Q].y - (ovalSize/2), ovalSize, ovalSize);
            g.drawOval(point[QRSDetector.R].x - (ovalSize/2), point[QRSDetector.R].y - (ovalSize/2), ovalSize, ovalSize);
            g.drawOval(point[QRSDetector.S].x - (ovalSize/2), point[QRSDetector.S].y - (ovalSize/2), ovalSize, ovalSize);

            startOfMeasurementsPositionX = currentPoint.x;
        }

        i++;
    }
}