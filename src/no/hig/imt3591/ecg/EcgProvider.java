package no.hig.imt3591.ecg;

import java.awt.*;

/**
 * Interface that provides interaction towards the sensor readings and the graph panel.
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
    public boolean getPeakDetectionMethod();
    public double getSATemperature();
    public double getSATemperatureLimit();
    public double getSAcoolDownRate();
    public int getObservationLimit();
    public int getSamplingFrequency();
}
