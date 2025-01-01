package com.pol.product_service.DTO.course;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CourseSummaryDTO {
    private UUID id;
    private String title;
    private String summary;
    private BigDecimal price;
    private String instructor;
}
