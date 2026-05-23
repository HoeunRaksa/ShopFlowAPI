package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;

    private String telegramLink;
    private String facebookLink;
    private String phoneNumber;
    private String role;
}