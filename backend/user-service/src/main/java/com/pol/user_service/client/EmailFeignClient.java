package com.pol.user_service.client;

import com.pol.user_service.dto.feign.ForgotPasswordRequestDto;
import com.pol.user_service.dto.feign.NewUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service")
public interface EmailFeignClient {
    @PostMapping("/send/user-registered")
    ResponseEntity sendWelcomeEmail(@RequestBody NewUserRequestDto newUserRequestDto);
    @PostMapping("/send/forgot-password")
    ResponseEntity sendForgotPasswordEmail(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto);
}

