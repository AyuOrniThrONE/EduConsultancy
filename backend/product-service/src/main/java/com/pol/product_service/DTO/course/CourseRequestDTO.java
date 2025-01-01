package com.pol.product_service.DTO.course;

import com.pol.product_service.entity.CourseStatus;
import com.pol.product_service.validation.customAnnotations.ValidCourseStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Please provide a title for the product.")
    @Size(max = 255, message = "Title can be max 255 characters long.")
    private String title;

    @NotBlank(message = "Please provide a description for the product.")
    private String description;

    @NotBlank(message = "Please provide a summary for the product.")
    @Size(max = 255, message = "Title can be max 255 characters long.")
    private String summary;

    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private BigDecimal price;

    @NotNull(message = "Status cannot be null")
    @ValidCourseStatus(allowedValues = {CourseStatus.ACTIVE, CourseStatus.INACTIVE}, message = "Status must be ACTIVE or INACTIVE")
    private CourseStatus status;

    @NotNull(message = "Category ID cannot be null")
    private UUID categoryId;
}

