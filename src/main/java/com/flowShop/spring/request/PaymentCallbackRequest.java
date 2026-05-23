package com.flowShop.spring.request;

public record PaymentCallbackRequest(
        Long paymentId,
        String paymentStatus
) {
}
