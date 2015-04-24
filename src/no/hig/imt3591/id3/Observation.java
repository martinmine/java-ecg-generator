package no.hig.imt3591.id3;

/**
 * Created by Martin on 24.04.2015.
 */
public class Observation {
    private final Class<? extends ITreeResult> resultType;
    private final Double[] tuple;

    public Observation(Class<? extends ITreeResult> resultType, Double[] tuple) {
        this.resultType = resultType;
        this.tuple = tuple;
    }

    public Class<? extends ITreeResult> getResultType() {
        return resultType;
    }

    public double getObservationValue(int index) {
        return tuple[index];
    }

    public ITreeResult createInstance() {
        try {
            return resultType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate a new instance of the tree output value", e);
        }
    }
}
