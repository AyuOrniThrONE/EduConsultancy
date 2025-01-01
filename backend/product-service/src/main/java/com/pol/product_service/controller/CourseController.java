package com.pol.product_service.controller;

import com.pol.product_service.DTO.course.CoursePageResponseDTO;
import com.pol.product_service.DTO.course.CoursePriceDTO;
import com.pol.product_service.DTO.course.CourseResponseDTO;
import com.pol.product_service.service.CourseService;
import com.pol.product_service.utils.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<CoursePageResponseDTO> getAllCourse(
            @RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
            @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
            @RequestParam(defaultValue = AppConstants.SORT_BY_COURSE_TITLE,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order
    ){
        CoursePageResponseDTO response = courseService.getAllCourse(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<CoursePageResponseDTO> searchByKeyword(
            @RequestParam(defaultValue = "",required = false,value ="keyword") String keyword,
            @RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
            @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
            @RequestParam(defaultValue = AppConstants.SORT_BY_COURSE_TITLE,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order
    ){
        return ResponseEntity.ok(courseService.searchByKeyword(keyword,page,size,sortBy,order));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable UUID id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping(value = "/price/{id}")
    public CoursePriceDTO getCoursePriceById(@PathVariable UUID id){
        return courseService.getCoursePriceById(id);
    }
}
