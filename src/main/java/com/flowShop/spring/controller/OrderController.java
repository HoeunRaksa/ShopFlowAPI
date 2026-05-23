package com.flowShop.spring.controller;

import com.flowShop.spring.Dtos.LocationRequest;
import com.flowShop.spring.response.ApiResponse;
import com.flowShop.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService service;
    @PostMapping("/create")
    public ApiResponse<String> createOrder(
            @RequestParam(required = false)
            Integer id,

            @RequestBody(required = false)
            LocationRequest request
    ){
        return service.createOrder(id, request);
    }
}
