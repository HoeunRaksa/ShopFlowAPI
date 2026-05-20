package com.flower.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User_Request_Contact {
    private String telegramLink;
    private String facebookLink;
    private String phoneNumber;
}
