package by.javaguru.profiler.usecasses.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SequenceNumbersValidator.class)
public @interface SequenceNumbersValidation {

    String message() default "Invalid sequence number: must be unique and fit list size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
