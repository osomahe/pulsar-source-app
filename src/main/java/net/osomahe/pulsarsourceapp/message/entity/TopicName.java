package net.osomahe.pulsarsourceapp.message.entity;

import net.osomahe.pulsarsourceapp.message.control.TopicValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TopicValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public @interface TopicName {
    String value() default "";

    String message() default "Not valid topic name";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
