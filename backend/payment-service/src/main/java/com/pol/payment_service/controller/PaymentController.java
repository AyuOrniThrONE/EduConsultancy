package com.pol.payment_service.controller;

import com.pol.payment_service.client.ProductFeignClient;
import com.pol.payment_service.dto.CoursePriceDTO;
import com.pol.payment_service.dto.PaymentRequestDTO;
import com.pol.payment_service.service.PaymentService;
import com.razorpay.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ProductFeignClient productFeignClient;
    public PaymentController(PaymentService paymentService, ProductFeignClient productFeignClient) {
        this.paymentService = paymentService;
        this.productFeignClient = productFeignClient;
    }

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @GetMapping
    public String working(){
        return  "WORKING";
    }

    @PostMapping("/create")
    public com.pol.payment_service.entity.Payment createOrder(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
        try {
            return paymentService.createOrder(paymentRequestDTO);
        } catch (RazorpayException e) {
            throw new RuntimeException("ORDER WENT WRONG");
        }
    }

    @PostMapping("/test/{id}")
    public CoursePriceDTO test(@PathVariable UUID id){
        return productFeignClient.getCoursePriceById(id);
    }

    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> paymentDetails) throws RazorpayException {
        return paymentService.updateOrder(paymentDetails);
    }
}
