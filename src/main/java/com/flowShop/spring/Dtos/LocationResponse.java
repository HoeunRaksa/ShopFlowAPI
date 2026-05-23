package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Integer id;
    private String receiverName;
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryNote;

    private Integer userId;
    private String userName;
    private String userEmail;
}