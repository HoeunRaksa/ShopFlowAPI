package com.flower.spring.controller;

import com.flower.spring.Dtos.LocationRequest;
import com.flower.spring.response.ApiResponse;
import com.flower.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
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
