package com.pol.product_service.validation.customValidation;

import com.pol.product_service.entity.CourseStatus;
import com.pol.product_service.validation.customAnnotations.ValidCourseStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CourseStatusValidator implements ConstraintValidator<ValidCourseStatus, CourseStatus> {

    private CourseStatus[] allowedValues;

    @Override
    public void initialize(ValidCourseStatus constraintAnnotation) {
        allowedValues = constraintAnnotation.allowedValues();
    }

    @Override
    public boolean isValid(CourseStatus status, ConstraintValidatorContext context) {
        return status == null || Arrays.asList(allowedValues).contains(status);
    }
}
