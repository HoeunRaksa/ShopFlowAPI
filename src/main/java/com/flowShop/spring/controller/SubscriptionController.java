package com.flowShop.spring.controller;

import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;
    @GetMapping
    public ResultMessage<List<Subscription>> get(){
        return service.getSub();
    }

}
