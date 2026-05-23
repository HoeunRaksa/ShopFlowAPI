package com.flowShop.spring.response;

import com.flowShop.spring.Enum.OrderStatus;
import com.flowShop.spring.Enum.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private Integer orderId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private Long paymentId;
    private String paymentCode;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
