package com.pol.email_service.controller;


import com.pol.email_service.dto.ForgotPasswordDto;
import com.pol.email_service.dto.NewUserDto;
import com.pol.email_service.dto.PaymentSuccessDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.pol.email_service.service.ResendService;


@RestController
@RequestMapping("/send")
public class EmailController {
    private final ResendService resendService;

    public EmailController(ResendService resendService) {
        this.resendService = resendService;
    }
    @GetMapping
    public String test() {
        return "Hello";
    }

    @PostMapping("/user-registered")
    public void userRegistered(@RequestBody @Valid NewUserDto newUserDto) {
        System.out.println("Request Received");
        resendService.sendWelcomeEmail(newUserDto.getEmail(), newUserDto.getName());
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto) {
        resendService.sendForgotPasswordOTPEmail(forgotPasswordDto.getEmail(), forgotPasswordDto.getName(), forgotPasswordDto.getOtp());
    }

    @PostMapping("/payment-successful")
    public void successfulPayment(@RequestBody @Valid PaymentSuccessDTO paymentSuccessDTO) {
        resendService.sendPaymentSuccesfulEmail(paymentSuccessDTO.getEmail(),paymentSuccessDTO.getName(), paymentSuccessDTO.getPaymentId(), paymentSuccessDTO.getProductId(), paymentSuccessDTO.getOrderId());
    }
}
