package com.pol.user_service.dto.feign;


import lombok.Data;

@Data
public class NewUserRequestDto {

    private String name;
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
