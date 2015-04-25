package no.hig.imt3591.id3.annotations;

import no.hig.imt3591.id3.AttributeType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declares that the field is an attribute that will be used in the decision tree.
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Attribute {
    AttributeType type();
    double[] outputValues() default {};
}
