package og.acm.ecg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 27.03.2015.
 *
 */
public class PeakDetection {
    private List<Double> frequencies;
    private double highFrequency[];
    private double lowFrequency[];
    private int numObservations;

    public static final int LOW = 0;
    public static final int HIGH = 1;

    public static final int P = 0;
    public static final int Q = 1;
    public static final int R = 2;
    public static final int S = 3;
    public static final int T = 4;

    private void updateFrequency() {

        double tempFrequencies[][] = new double[2][3];
        int tempTimestamp[][] = new int[2][3];

        tempFrequencies[LOW][0] = (frequencies.get(0) < frequencies.get(1))? frequencies.get(0): frequencies.get(1);
        tempTimestamp[LOW][0] = (frequencies.get(0) < frequencies.get(1))? 0: 1;

        tempFrequencies[HIGH][0] = (frequencies.get(0) > frequencies.get(1))? frequencies.get(0): frequencies.get(1);
        tempTimestamp[HIGH][0] = (frequencies.get(0) > frequencies.get(1))? 0: 1;

        tempFrequencies[LOW][1] = tempFrequencies[LOW][0];
        tempFrequencies[HIGH][1] = tempFrequencies[HIGH][0];
        tempFrequencies[HIGH][2] = tempFrequencies[HIGH][0];

        // TODO: known issue :/
        // Getting 2 lowest points doesn't work since these points would most probably be close to each other and therefor not be Q and S
        // but rather - lets say Q is the lowest point, the second most lowest point would then be pretty close to Q... (at least possible)
        // and I would guess highly probable.

        for (int i=2; i<numObservations; i++) {

            double current = frequencies.get(i);

            if (current > tempFrequencies[HIGH][0]) {
                if (current > tempFrequencies[HIGH][1]) {
                    if (current > tempFrequencies[HIGH][2]) {
                        tempFrequencies[HIGH][2] = current;
                        tempTimestamp[HIGH][2] = i;
                    } else {
                        tempFrequencies[HIGH][1] = current;
                        tempTimestamp[HIGH][1] = i;
                    }
                } else {
                    tempFrequencies[HIGH][0] = current;
                    tempTimestamp[HIGH][0] = i;
                }
            } else if (current < tempFrequencies[LOW][0]) {

                if (current < tempFrequencies[LOW][1]) {
                    tempFrequencies[LOW][0] = tempFrequencies[LOW][1];
                    tempTimestamp[LOW][0] = tempTimestamp[LOW][1];

                    tempFrequencies[LOW][1] = current;
                    tempTimestamp[LOW][1] = i;
                } else if (current < tempFrequencies[LOW][0]) {
                    tempFrequencies[LOW][0] = current;
                    tempTimestamp[LOW][0] = i;
                }
            }
        }

    }

    public PeakDetection(int num) {
        frequencies = new ArrayList<>();

        highFrequency = new double[3];  // P, R, T
        lowFrequency = new double[2];   // Q, S

        numObservations = num;
    }

    public void add(double d) {

        int size = frequencies.size();

        if (size >= numObservations) {
            frequencies.clear();
        }

        frequencies.add(d);

        if (frequencies.size() == numObservations) {
            updateFrequency();
        }
    }

    public double getFrequency(int type) {
        return (type == HIGH)? highFrequency[0]: lowFrequency[0];
    }
}
