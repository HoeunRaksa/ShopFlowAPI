package com.flowShop.spring.model;

import com.flowShop.spring.Enum.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
