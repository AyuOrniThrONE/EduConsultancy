package com.pol.product_service.mapper;

import com.pol.product_service.DTO.category.CategoryRequestDTO;
import com.pol.product_service.DTO.category.CategoryResponseDTO;
import com.pol.product_service.entity.Category;


public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .summary(dto.getSummary())
                .build();
    }

    public static CategoryResponseDTO toResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .summary(category.getSummary())
                .build();
    }
}
