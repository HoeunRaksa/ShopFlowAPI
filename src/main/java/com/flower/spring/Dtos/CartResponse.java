package com.flower.spring.Dtos;

import com.flower.spring.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CartResponse {
    private Integer id;   // ✅ fix name
    private Integer quantity;
    private Product product;
}