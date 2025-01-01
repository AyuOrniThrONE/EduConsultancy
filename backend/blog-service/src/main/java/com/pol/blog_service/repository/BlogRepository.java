package com.pol.blog_service.repository;


import com.pol.blog_service.dto.blog.BlogAdminResponseDTO;
import com.pol.blog_service.dto.blog.BlogResponseDTO;
import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.entity.Blog;
import com.pol.blog_service.entity.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {

    @Query(
            "SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title, b.heroImg, b.publishedAt, b.summary, b.status, b.author, b.authorId) " +
                    "FROM Blog b " +
                    "JOIN b.tags t " +
                    "WHERE t.id = :tagId " +
                    "AND b.status = com.pol.blog_service.entity.BlogStatus.PUBLISHED"
    )
    Page<BlogSummaryDTO> findPublishedBlogsByTagId(@Param("tagId") UUID tagId, Pageable pageable);

//    @Query(
//            "SELECT new com.pol.blog_service.dto.blog.BlogResponseDTO(b.id, b.title, b.content, b.publishedAt, b.tags) "+
//                    "FROM Blog b " +
//                    "JOIN b.tags t " +
//                    "WHERE b.id= :blogId "+
//                    "AND b.status = com.pol.blog_service.entity.BlogStatus.PUBLISHED"
//    )
//    Optional<BlogResponseDTO> findBlogByIdWithTags(@Param("blogId") UUID blogId);

    @Query(
            "SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title, b.heroImg, b.publishedAt, b.summary, b.status, b.author, b.authorId) " +
            "FROM Blog b " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))"  +
            "AND b.status = com.pol.blog_service.entity.BlogStatus.PUBLISHED"
    )
    Page<BlogSummaryDTO> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(
            "SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title,b.heroImg, b.publishedAt, b.summary, b.status, b.author, b.authorId) " +
            "FROM Blog b " +
            "WHERE b.status= com.pol.blog_service.entity.BlogStatus.PUBLISHED"
    )
    Page<BlogSummaryDTO> findAllBlogSummary(Pageable pageable);

    @Query(
            "SELECT new com.pol.blog_service.dto.blog.BlogAdminResponseDTO(b.id, b.title, b.heroImg, b.content, b.status) " +
                    "FROM Blog b"
    )
    Page<BlogAdminResponseDTO> findAllFullBlog(Pageable pageable);

    @Query("SELECT b FROM Blog b JOIN FETCH b.tags WHERE b.id = :blogId AND b.status = :status")
    Optional<Blog> findByIdAndStatusWithTags(@Param("blogId") UUID blogId, @Param("status") BlogStatus status);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM blog_tags WHERE blog_id = :blogId")
//    void deleteTagsByBlogId(@Param("blogId") UUID blogId);

}

