package com.pol.email_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ForgotPasswordDto {
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Otp is Required")
    private String otp;
}
