package com.flowShop.spring.Dtos;



import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String code;
}
