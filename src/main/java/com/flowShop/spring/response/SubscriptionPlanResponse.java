package com.flowShop.spring.response;


import lombok.Builder;

import java.util.List;

@Builder
public record SubscriptionPlanResponse(

        Long id,

        String title,

        Double price,

        String description,

        Boolean active,

        List<SubscriptionFeatureResponse> features

) {
}