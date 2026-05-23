package com.flowShop.spring.response;

import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.Enum.SubscriptionPlan;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeSubscriptionResponse {

    private Long paymentId;

    private String paymentCode;

    private Long subscriptionId;

    private SubscriptionPlan plan;

    private Double amount;

    private PaymentStatus paymentStatus;
}
