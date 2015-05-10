package no.hig.imt3591.ecg.decisions;

import no.hig.imt3591.id3.AttributeType;
import no.hig.imt3591.id3.annotations.Attribute;

/**
 * Created by marti_000 on 10.05.2015.
 */
public class SensorObservation {
    @Attribute(type = AttributeType.CATEGORICAL, outputValues = {0, 1})
    private double cardiacEvent;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double pulse;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double pulseChange;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double oxygen;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double oxygenChange;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double skin;

    @Attribute(type = AttributeType.CONTINUOUS)
    private double skinChange;

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
