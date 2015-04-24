package no.hig.imt3591.id3;

/**
 * Created by Martin on 24.04.2015.
 */
public class Observation {
    private boolean resultValue;
    private Double[] tuple;

    public Observation(boolean resultValue, Double[] tuple) {
        this.resultValue = resultValue;
        this.tuple = tuple;
    }

    /*
    public boolean getResultValue() {
        return resultValue;
    }*/

    public double getResultValue() {
        return resultValue ? 0 : 1;
    }

    public Double[] getTuple() {
        return tuple;
    }
}
