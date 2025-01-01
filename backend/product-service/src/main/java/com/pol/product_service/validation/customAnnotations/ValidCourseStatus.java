package com.pol.product_service.validation.customAnnotations;

import com.pol.product_service.entity.CourseStatus;
import com.pol.product_service.validation.customValidation.CourseStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseStatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCourseStatus {
    String message() default "Invalid course status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    CourseStatus[] allowedValues();
}
