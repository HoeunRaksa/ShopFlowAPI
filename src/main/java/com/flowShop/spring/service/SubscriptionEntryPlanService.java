package com.flowShop.spring.service;

import com.flowShop.spring.model.SubscriptionPlanEntity;
import com.flowShop.spring.repository.SubscriptionPlanEntityRepository;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionFeatureResponse;
import com.flowShop.spring.response.SubscriptionPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SubscriptionEntryPlanService {
    private final SubscriptionPlanEntityRepository repository;
    public ResultMessage<List<SubscriptionPlanResponse>> getSubscriptionEntry(){
        List<SubscriptionPlanResponse> data =
                repository.findAll()
                        .stream()
                        .map(this::toResponse)
                        .toList();
           return ResultMessage.success(1000, "success", data);
    }

    public SubscriptionPlanResponse toResponse(
            SubscriptionPlanEntity entity
    ){

        return SubscriptionPlanResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .active(entity.getActive())

                .features(
                        entity.getFeatures()
                                .stream()
                                .map(feature ->
                                        SubscriptionFeatureResponse.builder()
                                                .id(feature.getId())
                                                .feature(feature.getFeature())
                                                .build()
                                )
                                .toList()
                )

                .build();
    }



}
