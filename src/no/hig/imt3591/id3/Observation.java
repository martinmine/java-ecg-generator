package no.hig.imt3591.id3;

/**
 * Created by Martin on 24.04.2015.
 */
public class Observation {
    private Class<? extends ITreeResult> resultType;
    private Double[] tuple;

    public Observation(Class<? extends ITreeResult> resultType, Double[] tuple) {
        this.resultType = resultType;
        this.tuple = tuple;
    }

    public Class<? extends ITreeResult> getResultType() {
        return resultType;
    }

    public Double[] getTuple() {
        return tuple;
    }

    public ITreeResult createInstance() {
        try {
            return resultType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instanciate a new instance of the tree output value", e);
        }
    }
}
