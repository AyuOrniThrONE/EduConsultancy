package com.pol.product_service.DTO.course;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CoursePageResponseDTO {
    List<CourseSummaryDTO> courses;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
