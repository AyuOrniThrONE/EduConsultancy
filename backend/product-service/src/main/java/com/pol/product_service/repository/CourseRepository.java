package com.pol.product_service.repository;

import com.pol.product_service.DTO.course.CourseAdminResponse;
import com.pol.product_service.DTO.course.CoursePriceDTO;
import com.pol.product_service.DTO.course.CourseResponseDTO;
import com.pol.product_service.DTO.course.CourseSummaryDTO;
import com.pol.product_service.entity.Course;
import com.pol.product_service.entity.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query(
            "SELECT new com.pol.product_service.DTO.course.CourseSummaryDTO(c.id, c.title, c.summary, c.price, c.instructor) " +
                    "FROM Course c " +
                    "WHERE c.status = :status"
    )
    Page<CourseSummaryDTO> findAllCoursesByStatus(@Param("status") CourseStatus status, Pageable pageable);

    @Query(
            "SELECT new com.pol.product_service.DTO.course.CourseAdminResponse(c.id, c.title, c.description, c.summary, c.price, c.status," +
                    "new com.pol.product_service.DTO.category.CategorySummaryResponseDTO(c.category.id, c.category.name)) " +
                    "FROM Course c"
    )
    Page<CourseAdminResponse> findAllFullCourses(Pageable pageable);

    @Query(
            "SELECT new com.pol.product_service.DTO.course.CourseSummaryDTO(c.id, c.title, c.summary, c.price, c.instructor) " +
                    "FROM Course c " +
                    "WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))"  +
                    "AND c.status = com.pol.product_service.entity.CourseStatus.ACTIVE"
    )
    Page<CourseSummaryDTO> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


    @Query(
            "SELECT new com.pol.product_service.DTO.course.CoursePriceDTO(c.id, c.price) " +
                    "FROM Course c "+
                    "WHERE c.status= com.pol.product_service.entity.CourseStatus.ACTIVE " +
                    "AND c.id= :courseId"
    )
    CoursePriceDTO getCoursePriceById(@Param("courseId") UUID id);
}
