package com.flowShop.spring.controller;

import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.request.UpgradeSubscriptionRequest;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionHistoryResponse;
import com.flowShop.spring.response.UpgradeSubscriptionResponse;
import com.flowShop.spring.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/upgrade-subscription")
    public ResultMessage<UpgradeSubscriptionResponse> upgradeSubscription(
            @RequestBody UpgradeSubscriptionRequest request
    ) {
        return service.upgradeSubscription(request);
    }

    @GetMapping("/subscriptions")
    public ResultMessage<List<SubscriptionHistoryResponse>> getSubscriptionHistory() {
        return service.getSubscriptionHistory();
    }

}
