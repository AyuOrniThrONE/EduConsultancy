package com.pol.blog_service.dto.blog;

import com.pol.blog_service.dto.tags.TagSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDTO {
    private UUID id;
    private String title;
    private String heroImg;
    private String summary;
    private String content;
    private String publishedAt;
    private String author;
    private UUID authorId;
    private Set<TagSummaryDTO> tags= new HashSet<>();
}
