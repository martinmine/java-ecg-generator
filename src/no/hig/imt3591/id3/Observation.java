package no.hig.imt3591.id3;

import java.lang.reflect.Field;

/**
 * Contains information about one observation/tuple that is fed to the decision tree.
 */
public class Observation<T> {
    private final Class<? extends ITreeResult> resultType;
    private final T observationData;

    /**
     * Creates a new observation.
     * @param resultType The type of the action that should be invoked for this observation.
     * @param observationData Contains information about the observation.
     */
    public Observation(Class<? extends ITreeResult> resultType, T observationData) {
        this.resultType = resultType;
        this.observationData = observationData;
    }

    /**
     * @return The desired outcome of this observation.
     */
    public Class<? extends ITreeResult> getResultType() {
        return resultType;
    }

    /**
     * Gets the observation value for a given attribute.
     * @param field The attribute/field we want information from.
     * @return Observation value.
     */
    public double getObservationValue(Field field) {
        field.setAccessible(true);

        try {
            return (Double) field.get(observationData);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Setting the field accessible failed", e);
        }
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
