package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private String description;
}
