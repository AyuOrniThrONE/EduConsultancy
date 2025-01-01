package com.pol.blog_service.controller;

import com.pol.blog_service.dto.blog.BlogAdminPageResponseDTO;
import com.pol.blog_service.dto.blog.BlogPageResponseDTO;
import com.pol.blog_service.dto.blog.BlogRequestDTO;
import com.pol.blog_service.dto.blog.BlogResponseDTO;
import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.service.blog.BlogService;
import com.pol.blog_service.service.tags.TagsService;
import com.pol.blog_service.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BlogService blogService;
    private final TagsService tagsService;

    public AdminController(BlogService blogService, TagsService tagsService) {
        this.blogService = blogService;
        this.tagsService = tagsService;
    }

    @GetMapping("/blogs")
    public ResponseEntity<BlogAdminPageResponseDTO> getAllBlogs(
            @RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
            @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
            @RequestParam(defaultValue = AppConstants.SORT_BY_BLOG_PUBLISHED_AT,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order
    ){
        return ResponseEntity.ok(blogService.getAllBlogsAdmin(page,size,sortBy,order));
    }

    @PostMapping("/blogs")
    public ResponseEntity<BlogResponseDTO> createBlog(@RequestBody @Valid BlogRequestDTO blogRequestDTO,
                                                      @RequestHeader("X-User-Id") String userId,
                                                      @RequestHeader("X-User-Name") String username){
        return ResponseEntity.ok(blogService.createBlog(blogRequestDTO,userId,username));
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<BlogResponseDTO> updateBlogById(@PathVariable UUID id,
                                                          @RequestBody @Valid BlogRequestDTO blogRequestDTO,
                                                          @RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(blogService.updateBlog(blogRequestDTO,id,userId));
    }

    @DeleteMapping("/blogs/{id}")
    public void deleteBlogById(@PathVariable UUID id,
                               @RequestHeader("X-User-Id") String userId){
        blogService.deleteBlogById(id,userId);
    }


    @PostMapping("/tags")
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody @Valid TagRequestDTO tagRequestDTO){
        System.out.println(tagRequestDTO);
        return ResponseEntity.ok(tagsService.createTag(tagRequestDTO));
    }

    @DeleteMapping("/tags/{id}")
    public void deleteTagById(@PathVariable UUID id){
        tagsService.deleteTagById(id);
    }
}
