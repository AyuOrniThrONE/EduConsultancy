package com.pol.user_service.dto.feign;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordRequestDto {
    private String email;
    private String name;
    private String otp;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
