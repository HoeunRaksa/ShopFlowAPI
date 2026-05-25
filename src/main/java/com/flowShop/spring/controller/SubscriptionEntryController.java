package com.flowShop.spring.controller;

import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionPlanResponse;
import com.flowShop.spring.service.SubscriptionEntryPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptionEntry")
@RequiredArgsConstructor
public class SubscriptionEntryController {
    private final SubscriptionEntryPlanService service;
        @GetMapping
        public ResultMessage<List<SubscriptionPlanResponse>> getSubscriptionEntry (){return service.getSubscriptionEntry(); }
}
