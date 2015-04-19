package no.hig.imt3591.id3;

/**
 * Provider for the attribute type in a set.
 */
public interface IAttributeMetadataProvider {
    /**
     * Means that the attribute contains of a finite set, eg. {CARS, BIKES, MOTORCYCLES}
     */
    public static final int CATEGORICAL = 0;

    /**
     * The attribute is a number.
     */
    public static final int CONTINUOUS = 1;

    /**
     * Gets the type of an attribute.
     * @param attributeIndex Index of the attribute.
     * @return The attribute type.
     */
    int getAttributeType(int attributeIndex);
}
