package no.hig.imt3591.id3;

/**
 * Provider for attribute values, doesn't have to return anything on continuous attributes.
 */
public interface IAttributeNameProvider {
    /**
     * Returns the categories for a set.
     * @param i Index of the attribute.
     * @return The different categories.
     */
    Integer[] getAttributeValues(int i);
}
