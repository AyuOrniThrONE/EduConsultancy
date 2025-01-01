package com.pol.blog_service.mapper;

import com.pol.blog_service.dto.blog.BlogRequestDTO;
import com.pol.blog_service.dto.blog.BlogResponseDTO;
import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.dto.blog.BlogAdminResponseDTO;
import com.pol.blog_service.entity.Blog;

import java.util.stream.Collectors;

public class BlogMapper {
    public static Blog toEntity(BlogRequestDTO blogRequestDTO){
        return Blog.builder()
                .title(blogRequestDTO.getTitle())
                .content(blogRequestDTO.getContent())
                .status(blogRequestDTO.getStatus())
                .heroImg(blogRequestDTO.getHeroImg())
                .build();
    }

    public static BlogResponseDTO toResponseDTO(Blog blog){
        return BlogResponseDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .heroImg(blog.getHeroImg())
                .content(blog.getContent())
                .summary(blog.getSummary())
                .author(blog.getAuthor())
                .authorId(blog.getAuthorId())
                .publishedAt(blog.getPublishedAt().toString())
                .tags(blog.getTags().stream().map(TagsMapper::toSummaryDTO).collect(Collectors.toSet()))
                .build();
    }

//    public static BlogAdminResponseDTO toAdminResponseDTO(Blog blog){
//        return BlogAdminResponseDTO.builder()
//                .id(blog.getId())
//                .title(blog.getTitle())
//                .heroImg(blog.getHeroImg())
//                .content(blog.getContent())
//                .status(blog.getStatus())
//                .build();
//    }
}
