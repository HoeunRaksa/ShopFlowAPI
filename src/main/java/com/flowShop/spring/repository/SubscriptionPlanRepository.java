package com.flowShop.spring.repository;

import com.flowShop.spring.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository
        extends JpaRepository<Subscription, Long> {
}