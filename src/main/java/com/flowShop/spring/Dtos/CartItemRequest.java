package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemRequest {
    private Integer productId;
    private Integer quantity;
}
