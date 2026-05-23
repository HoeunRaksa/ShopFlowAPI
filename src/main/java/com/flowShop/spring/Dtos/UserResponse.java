package com.flowShop.spring.Dtos;

import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.response.SubscriptionResponse;
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
    private SubscriptionResponse subscription;

    private String telegramLink;
    private String facebookLink;
    private String phoneNumber;
    private String role;
}