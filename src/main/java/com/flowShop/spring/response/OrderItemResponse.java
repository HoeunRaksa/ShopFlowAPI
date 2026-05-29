package com.flowShop.spring.response;

import com.flowShop.spring.Dtos.ProductResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime dateTime;
}
