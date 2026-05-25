package com.flowShop.spring.response;


import lombok.Builder;

@Builder
public record SubscriptionFeatureResponse(
        Long id,
        String feature
) {
}