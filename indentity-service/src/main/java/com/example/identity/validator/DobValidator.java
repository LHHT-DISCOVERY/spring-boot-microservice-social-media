package com.example.identity.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// step3 validator
// implement Annotation custom and datatype we want to validate (DobConstraint for field LocalDate)
public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int min;

    //  when initialize we need know value min() they enter , this mean get value in this step .
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        //      get min , this method initialize before isValid() method
        min = constraintAnnotation.min();
    }

    //    method process this data true or false ?
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        //        process business in this method
        if (Objects.isNull(localDate)) return true;
        long years = ChronoUnit.YEARS.between(localDate, LocalDate.now());
        return years >= min;
    }
}
