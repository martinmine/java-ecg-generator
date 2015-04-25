package no.hig.imt3591.id3;

/**
 * Contains information about one observation/tuple that is fed to the decision tree.
 */
public class Observation {
    private final Class<? extends ITreeResult> resultType;
    private final Double[] tuple;

    /**
     * Creates a new observation.
     * @param resultType The type of the action that should be invoked for this observation.
     * @param tuple Observation data.
     */
    public Observation(Class<? extends ITreeResult> resultType, Double[] tuple) {
        this.resultType = resultType;
        this.tuple = tuple;
    }

    /**
     * @return The desired outcome of this observation.
     */
    public Class<? extends ITreeResult> getResultType() {
        return resultType;
    }

    /**
     * Gets the observation value for a given attribute.
     * @param index Index of the observation value (attribute index).
     * @return Observation value.
     */
    public double getObservationValue(int index) {
        return tuple[index];
    }

    /**
     * @return A new instance of the object that should be invoked for this type of observation.
     */
    public ITreeResult createInstance() {
        try {
            return resultType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate a new instance of the tree output value", e);
        }
    }
}
