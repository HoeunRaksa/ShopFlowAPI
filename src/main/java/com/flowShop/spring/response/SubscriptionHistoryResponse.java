package com.flowShop.spring.response;

import com.flowShop.spring.Enum.SubscriptionPlan;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionHistoryResponse {
    private Long id;
    private SubscriptionPlan plan;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}
