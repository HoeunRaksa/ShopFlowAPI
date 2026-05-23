package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String imageUrl;

    private Boolean isActive;

    // Instead of sending full Category object
    private Integer categoryId;

    private String categoryName;

    private Integer userId;
}