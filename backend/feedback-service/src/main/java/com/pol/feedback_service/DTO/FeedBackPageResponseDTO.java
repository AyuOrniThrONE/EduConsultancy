package com.pol.feedback_service.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FeedBackPageResponseDTO {
    List<FeedBackResponseDTO> feedbacks;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}

