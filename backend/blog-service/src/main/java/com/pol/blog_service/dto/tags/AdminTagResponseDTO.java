package com.pol.blog_service.dto.tags;

import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminTagResponseDTO {
    private UUID id;
    private String tagName;
    private String slug;
}
