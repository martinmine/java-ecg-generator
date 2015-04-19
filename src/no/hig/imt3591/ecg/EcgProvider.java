package no.hig.imt3591.ecg;

import java.awt.*;

/**
 * Created by Martin on 10.04.2015.
 *
 */
public interface EcgProvider {
    public double getVoltage();
    public double getTime();
    public double getPulse();
    public double getOxygenSaturation();
    public double getSkinResistance();
    public Graphics getGraphGraphics();
    public Point getLastPoint();
    public Point getCurrentPoint();
    public int getFrameAmplitude();
    public double getAmplitude();
    public double getPlotZoom();
}
