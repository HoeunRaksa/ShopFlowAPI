package com.flower.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String imageUrl;

    private Boolean isActive;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}