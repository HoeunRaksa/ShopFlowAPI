package com.flowShop.spring.service;

import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.repository.SubscriptionPlanRepository;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
      final SubscriptionPlanRepository repository;
    public ResultMessage<List<Subscription>> getSub(){
        List<Subscription> subscriptions =
                repository.findAll();

        return ResultMessage.success(
                1000,
                "Get subscriptions successfully",
                subscriptions
        );
    }
}
