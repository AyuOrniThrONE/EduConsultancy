package com.pol.product_service.DTO.category;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryResponseDTO {
    private UUID id;
    private String name;
    private String slug;
    private String summary;
}
