package com.flowShop.spring.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Integer orderId;
    private Integer buyerId;
    private String buyerName;
    private String buyerEmail;
    private Double totalPrice;
    private String status;
}