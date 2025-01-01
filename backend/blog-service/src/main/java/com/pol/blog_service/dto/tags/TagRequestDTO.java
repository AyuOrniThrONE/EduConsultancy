package com.pol.blog_service.dto.tags;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {
    @NotBlank(message = "Please provide a tag name")
    private String tagName;
}
