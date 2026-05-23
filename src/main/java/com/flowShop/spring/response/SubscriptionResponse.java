package com.flowShop.spring.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {

    private String plan;

    private String displayName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean active;
}
