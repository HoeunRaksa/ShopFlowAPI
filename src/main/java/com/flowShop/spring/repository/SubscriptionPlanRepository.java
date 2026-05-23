package com.flowShop.spring.repository;

import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionPlanRepository
        extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserOrderByStartDateDesc(User user);
}