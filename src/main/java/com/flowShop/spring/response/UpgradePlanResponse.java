package com.flowShop.spring.response;

import com.flowShop.spring.Enum.SubscriptionPlan;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpgradePlanResponse {

    private Long userId;

    private SubscriptionPlan plan;

    private String planName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean active;
}