package com.flowShop.spring.repository;

import com.flowShop.spring.model.SubscriptionPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanEntityRepository
        extends JpaRepository<SubscriptionPlanEntity, Long> {

}