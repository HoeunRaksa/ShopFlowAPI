package com.flower.spring.controller;

import com.flower.spring.Dtos.LocationRequest;
import com.flower.spring.response.ApiResponse;
import com.flower.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService service;
    @PostMapping("/create")
    public ApiResponse<String> createOrder(@RequestPart Integer Id, @RequestBody LocationRequest request){
         return service.createOrder(Id, request);
    }
}
