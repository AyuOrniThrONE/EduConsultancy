package com.pol.email_service.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentSuccessDTO {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "productId is required")
    private String productId;
    @NotBlank(message = "orderId is required")
    private String orderId;
    @NotBlank(message = "paymentId is required")
    private String paymentId;
}
