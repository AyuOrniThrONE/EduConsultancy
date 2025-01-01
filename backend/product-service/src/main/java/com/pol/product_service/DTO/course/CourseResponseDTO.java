package com.pol.product_service.DTO.course;

import com.pol.product_service.DTO.category.CategoryResponseDTO;
import com.pol.product_service.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CourseResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String summary;
    private BigDecimal price;
    private CategoryResponseDTO category;
    private String instructor;
    private UUID instructorId;
}
