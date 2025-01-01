package com.pol.product_service.service;

import com.pol.product_service.DTO.category.CategoryRequestDTO;
import com.pol.product_service.DTO.category.CategoryResponseDTO;
import com.pol.product_service.entity.Category;
import com.pol.product_service.exceptions.customExceptions.CategoryNotFoundException;
import com.pol.product_service.mapper.CategoryMapper;
import com.pol.product_service.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.save(CategoryMapper.toEntity(categoryRequestDTO));
        return CategoryMapper.toResponseDTO(category);
    }

    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found with id : "+id));
        category.setName(categoryRequestDTO.getName());
        category.setSummary(categoryRequestDTO.getSummary());
        return CategoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    public Category getCategoryById(UUID id){
        return categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found with id : "+id));
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.getAllCategories();
    }

    @Transactional
    public void deleteCategoryById(UUID id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category not found with id : "+id));
        category.getCourses().forEach(course -> course.setCategory(null));
        categoryRepository.deleteById(id);
    }
}
