package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CartResponse {
    private Integer id;   // ✅ fix name
    private Integer quantity;
    private ProductResponse product;
}