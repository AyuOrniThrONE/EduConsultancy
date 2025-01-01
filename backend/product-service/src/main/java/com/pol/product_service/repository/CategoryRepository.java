package com.pol.product_service.repository;

import com.pol.product_service.DTO.category.CategoryResponseDTO;
import com.pol.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query(
            "SELECT new com.pol.product_service.DTO.category.CategoryResponseDTO(cat.id, cat.name, cat.slug, cat.summary) "+
                    "FROM Category cat"
    )
    List<CategoryResponseDTO> getAllCategories();
}
