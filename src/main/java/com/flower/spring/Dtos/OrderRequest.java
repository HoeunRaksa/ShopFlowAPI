package com.flower.spring.Dtos;

import com.flower.spring.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderRequest {
    private List<OrderItem> items;
}
