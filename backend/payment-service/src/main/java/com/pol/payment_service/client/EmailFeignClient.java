package com.pol.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.pol.payment_service.dto.PaymentSuccessRequestDto;

@FeignClient(name = "email-service")
public interface EmailFeignClient {
    @PostMapping("/send/payment-successful")
    ResponseEntity sendPaymentSuccessEmail(@RequestBody PaymentSuccessRequestDto paymentSuccessRequest);
}
