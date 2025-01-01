package com.pol.product_service.DTO.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Please provide a name for the category.")
    private String name;

    @NotBlank(message = "Please provide a summary for the category.")
    @Size(max = 50, message = "summary shouldn't contain more than 50 characters.")
    private String summary;
}
