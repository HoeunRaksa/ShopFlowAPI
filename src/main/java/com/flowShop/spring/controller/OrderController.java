package com.flowShop.spring.controller;

import com.flowShop.spring.Dtos.LocationRequest;
import com.flowShop.spring.response.CreateOrderResponse;
import com.flowShop.spring.response.OrderHistoryResponse;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public ResultMessage<CreateOrderResponse> createOrder(
            @RequestParam(required = false)
            Integer id,

            @RequestBody(required = false)
            LocationRequest request
    ){
        return service.createOrder(id, request);
    }

    @GetMapping
    public ResultMessage<List<OrderHistoryResponse>> getOrderHistory() {
        return service.getOrderHistory();
    }
}
