package com.flowShop.spring.response;

import com.flowShop.spring.Dtos.ProductResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private ProductResponse product;
    private Integer quantity;
    private Double price;
}
