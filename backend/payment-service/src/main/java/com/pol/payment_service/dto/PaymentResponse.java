package com.pol.payment_service.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    String paymentId;
    String orderId;
    String signature;
}
