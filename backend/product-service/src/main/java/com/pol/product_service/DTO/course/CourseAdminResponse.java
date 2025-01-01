package com.pol.product_service.DTO.course;
import com.pol.product_service.DTO.category.CategorySummaryResponseDTO;
import com.pol.product_service.entity.Category;
import com.pol.product_service.entity.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CourseAdminResponse {
    private UUID id;
    private String title;
    private String description;
    private String summary;
    private BigDecimal price;
    private CourseStatus status;
    private CategorySummaryResponseDTO category;
}
