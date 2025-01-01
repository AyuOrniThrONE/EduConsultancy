package com.pol.email_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserDto {
    @NotBlank(message = "Please provide email.")
    private String email;
    @NotBlank(message = "Please provide name.")
    private String name;
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
