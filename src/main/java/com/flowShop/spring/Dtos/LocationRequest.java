package com.flowShop.spring.Dtos;

import lombok.Data;

@Data
public class LocationRequest {
    private String receiverName;
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryNote;
}
