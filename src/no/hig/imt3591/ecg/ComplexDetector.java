package no.hig.imt3591.ecg;

import og.acm.ecg.PeakDetection;

import java.awt.*;
import java.util.logging.Logger;

/**
 * Created by Martin on 17.04.2015.
 *
 */
public class ComplexDetector {
    private static final Logger LOGGER = Logger.getLogger(ComplexDetector.class.getSimpleName());
    private static PeakDetection pk = new PeakDetection(10);

    public static void onSignalReceived(EcgProvider ecgProvider) {
        double voltage = ecgProvider.getVoltage();
        double pulse = ecgProvider.getPulse();
        Graphics g = ecgProvider.getGraphGraphics();
        Point point = ecgProvider.getLastPoint();
        int frameAmplitude = ecgProvider.getFrameAmplitude(); // 145 = drawing position on 0.0 in Y position
        double amplitude = ecgProvider.getAmplitude();

        pk.add(voltage);

        LOGGER.info("amp: " + amplitude);
        double highPoint = pk.getFrequency(PeakDetection.HIGH);
        int voltageYPosition = (int)(frameAmplitude - (highPoint * (frameAmplitude / 140)));

        g.drawLine(point.x, point.y, point.x + 50, point.y);

        LOGGER.info("v = " + voltage + " @" + pulse);
    }
}
