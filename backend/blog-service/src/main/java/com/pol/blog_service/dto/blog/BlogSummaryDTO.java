package com.pol.blog_service.dto.blog;

import com.pol.blog_service.entity.BlogStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BlogSummaryDTO {
    private UUID id;
    private String title;
    private String publishedAt;
    private String summary;
    private String heroImg;
    private BlogStatus status;
    private String author;
    private UUID authorId;

    public BlogSummaryDTO(UUID id, String title, String heroImg, LocalDateTime publishedAt, String summary, BlogStatus status,String author,UUID authorId) {
        this.id = id;
        this.title = title;
        this.publishedAt = publishedAt.toString();
        this.summary = summary;
        this.status=status;
        this.author=author;
        this.authorId=authorId;
        this.heroImg = heroImg;
    }

}
