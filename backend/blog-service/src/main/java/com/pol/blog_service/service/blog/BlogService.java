package com.pol.blog_service.service.blog;

import com.pol.blog_service.dto.blog.*;

import java.util.UUID;

public interface BlogService {
    BlogResponseDTO createBlog(BlogRequestDTO blogRequestDTO,String userId, String username);
    BlogResponseDTO updateBlog(BlogRequestDTO blogRequestDTO, UUID id,String userId);
    BlogResponseDTO getBlogById(UUID id);
    void deleteBlogById(UUID id,String userId);
    BlogPageResponseDTO getAllBlogs(int page, int size, String sortBy, String order);
    BlogAdminPageResponseDTO getAllBlogsAdmin(int page, int size, String sortBy, String order);
    BlogPageResponseDTO searchBlogsByKeyword(String keyword,int page, int size, String sortBy, String order);
}
