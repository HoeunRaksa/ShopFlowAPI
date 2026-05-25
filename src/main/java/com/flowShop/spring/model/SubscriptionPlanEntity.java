package com.flowShop.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private String description;
    @OneToMany(mappedBy = "subscriptionPlan",
            cascade = CascadeType.ALL)
    private List<SubscriptionFeature> features;
    private Boolean active;
}