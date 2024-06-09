package com.example.identity.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// step1 validator
// where this annotation(@Target) be apply? FILED apply to properties in object class
@Target({ElementType.FIELD})
// when this annotation(@Target) be processed?
@Retention(RetentionPolicy.RUNTIME)
// class response process for this validation
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
