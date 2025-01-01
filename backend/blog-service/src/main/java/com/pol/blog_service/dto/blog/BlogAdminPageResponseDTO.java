package com.pol.blog_service.dto.blog;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BlogAdminPageResponseDTO {
    private List<BlogAdminResponseDTO> blogs;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
