package no.hig.imt3591.id3;

import no.hig.imt3591.id3.annotations.Attribute;

/**
 * Contains information about a tennis operation.
 */
public class TennisObservation {
    @Attribute(type = AttributeType.CATEGORICAL, outputValues = {0, 1, 2})
    private double outlook;

    @Attribute(type = AttributeType.CATEGORICAL, outputValues = {0, 1})
    private double sunny;

    @Attribute(type = AttributeType.CATEGORICAL, outputValues = {0, 1})
    private double humidity;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double temperature;

    public TennisObservation(double outlook, double sunny, double humidity, double temperature) {
        this.outlook = outlook;
        this.sunny = sunny;
        this.humidity = humidity;
        this.temperature = temperature;
    }
}
