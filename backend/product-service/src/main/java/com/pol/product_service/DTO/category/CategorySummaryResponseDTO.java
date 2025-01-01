package com.pol.product_service.DTO.category;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder

public class CategorySummaryResponseDTO {
    private UUID id;
    private String name;
}
