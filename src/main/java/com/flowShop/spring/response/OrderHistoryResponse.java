package com.flowShop.spring.response;

import com.flowShop.spring.Enum.OrderStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryResponse {
    private Integer id;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createAt;
    private List<OrderItemResponse> items;
}
