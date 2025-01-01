package com.pol.product_service.mapper;

import com.pol.product_service.DTO.course.CourseRequestDTO;
import com.pol.product_service.DTO.course.CourseResponseDTO;
import com.pol.product_service.entity.Category;
import com.pol.product_service.entity.Course;
import com.pol.product_service.entity.CourseStatus;

public class CourseMapper {

    public static Course toEntity(CourseRequestDTO dto) {
        return Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .summary(dto.getSummary())
                .price(dto.getPrice())
                .status(dto.getStatus())
                .build();
    }

    public static CourseResponseDTO toResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .summary(course.getSummary())
                .price(course.getPrice())
                .instructor(course.getInstructor())
                .instructorId(course.getInstructorId())
                .category(course.getCategory()==null?null:CategoryMapper.toResponseDTO(course.getCategory()))
                .build();
    }
}