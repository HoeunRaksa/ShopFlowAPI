package com.flower.spring.Dtos;

import lombok.Data;

@Data
public class CategoryResponse {
   private Integer id;
   private String name;
   private String description;
   private String imageUrl;
}
