package com.pol.product_service.service;

import com.pol.product_service.DTO.course.*;
import com.pol.product_service.entity.Course;
import com.pol.product_service.entity.CourseStatus;
import com.pol.product_service.exceptions.customExceptions.CategoryNotFoundException;
import com.pol.product_service.exceptions.customExceptions.CourseNotFoundException;
import com.pol.product_service.exceptions.customExceptions.UnauthorizedActionException;
import com.pol.product_service.mapper.CourseMapper;
import com.pol.product_service.repository.CategoryRepository;
import com.pol.product_service.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseService(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO,String userId,String username){
        Course course = CourseMapper.toEntity(courseRequestDTO);
        course.setInstructor(username);
        course.setInstructorId(UUID.fromString(userId));
        course.setCategory(categoryRepository.findById(courseRequestDTO.getCategoryId())
                .orElseThrow(()->new CourseNotFoundException("Category not found with id : "+courseRequestDTO.getCategoryId())));
        return CourseMapper.toResponseDTO(courseRepository.save(course));
    }

    public CourseResponseDTO getCourseById(UUID id){
        return CourseMapper.toResponseDTO(
                courseRepository.findById(id).orElseThrow(
                        ()->new CourseNotFoundException("Course not found with id : "+id)));
    }

    public CoursePriceDTO getCoursePriceById(UUID id){
        return courseRepository.getCoursePriceById(id);
    }

    @Transactional
    public void deleteCourseById(UUID id,String userId){
        Course course = courseRepository.findById(id).orElseThrow(()->new CourseNotFoundException("Course not found with id : "+id));
        if(!UUID.fromString(userId).equals(course.getInstructorId())){
            throw new UnauthorizedActionException("You are not authorized to delete this blog.");
        }
        course.setCategory(null);
        courseRepository.save(course);
        courseRepository.deleteById(id);
    }

    public CourseResponseDTO updateCourseById(UUID id, CourseRequestDTO courseRequestDTO,String userId){
        Course course = courseRepository.findById(id).orElseThrow(()->new CourseNotFoundException("Course not found with id : "+id));
        if(!UUID.fromString(userId).equals(course.getInstructorId())){
            throw new UnauthorizedActionException("You are not authorized to Update this course.");
        }
        course.setTitle(courseRequestDTO.getTitle());
        course.setDescription(courseRequestDTO.getDescription());
        course.setSummary(courseRequestDTO.getSummary());
        course.setPrice(courseRequestDTO.getPrice());
        course.setStatus(courseRequestDTO.getStatus());
        course.setCategory(categoryRepository.findById(courseRequestDTO.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category not found with id :"+courseRequestDTO.getCategoryId())));
        return CourseMapper.toResponseDTO(courseRepository.save(course));
    }

    public CoursePageResponseDTO getAllCourse(int page, int size, String sortBy, String order){
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<CourseSummaryDTO> courseSummaryDTOPage = courseRepository.findAllCoursesByStatus(CourseStatus.ACTIVE,pageable);
        return CoursePageResponseDTO.builder()
                .courses(courseSummaryDTOPage.getContent())
                .currentPage(courseSummaryDTOPage.getNumber())
                .totalPages(courseSummaryDTOPage.getTotalPages())
                .totalElements(courseSummaryDTOPage.getTotalElements())
                .pageSize(courseSummaryDTOPage.getSize())
                .hasNext(courseSummaryDTOPage.hasNext())
                .hasPrevious(courseSummaryDTOPage.hasPrevious())
                .build();
    }

    public CourseFullDetailPageResponseDTO getAllCourseFullDetails(int page, int size, String sortBy, String order){
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<CourseAdminResponse> courseResponseDTOPage = courseRepository.findAllFullCourses(pageable);
        return CourseFullDetailPageResponseDTO.builder()
                .courses(courseResponseDTOPage.getContent())
                .currentPage(courseResponseDTOPage.getNumber())
                .totalPages(courseResponseDTOPage.getTotalPages())
                .totalElements(courseResponseDTOPage.getTotalElements())
                .pageSize(courseResponseDTOPage.getSize())
                .hasNext(courseResponseDTOPage.hasNext())
                .hasPrevious(courseResponseDTOPage.hasPrevious())
                .build();
    }

    public CoursePageResponseDTO searchByKeyword(String keyword,int page, int size, String sortBy, String order){
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<CourseSummaryDTO> courseSummaryDTOPage = courseRepository.searchByKeyword(keyword,pageable);
        return CoursePageResponseDTO.builder()
                .courses(courseSummaryDTOPage.getContent())
                .currentPage(courseSummaryDTOPage.getNumber())
                .totalPages(courseSummaryDTOPage.getTotalPages())
                .totalElements(courseSummaryDTOPage.getTotalElements())
                .pageSize(courseSummaryDTOPage.getSize())
                .hasNext(courseSummaryDTOPage.hasNext())
                .hasPrevious(courseSummaryDTOPage.hasPrevious())
                .build();
    }
}
