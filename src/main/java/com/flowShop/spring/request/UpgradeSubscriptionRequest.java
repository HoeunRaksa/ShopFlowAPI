package com.flowShop.spring.request;

public record UpgradeSubscriptionRequest(
        Long subscriptionId,
        String cardNumber,
        String expiryMonth,
        String expiryYear,
        String securityCode
) {
}
