package no.hig.imt3591.ecg.decisions;

import no.hig.imt3591.id3.AttributeType;
import no.hig.imt3591.id3.annotations.Attribute;

/**
 * A sensor observation that contains different sensor values for one observation.
 */
public class SensorObservation {
    @Attribute(type = AttributeType.CATEGORICAL, outputValues = {0, 1})
    private final double cardiacEvent;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double pulse;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double pulseChange;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double oxygen;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double oxygenChange;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double skin;

    @Attribute(type = AttributeType.CONTINUOUS)
    private final double skinChange;

    public SensorObservation(double cardiacEvent, double pulse, double pulseChange, double oxygen, double oxygenChange, double skin, double skinChange) {
        this.cardiacEvent = cardiacEvent;
        this.pulse = pulse;
        this.pulseChange = pulseChange;
        this.oxygen = oxygen;
        this.oxygenChange = oxygenChange;
        this.skin = skin;
        this.skinChange = skinChange;
    }
}
