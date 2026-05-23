package com.flowShop.spring.seeder;

import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SubscriptionPlanSeeder {

    @Bean
    CommandLineRunner seedSubscription(
            SubscriptionPlanRepository subscriptionRepository
    ) {

        return args -> {

            if (subscriptionRepository.count() > 0) {
                return;
            }

            subscriptionRepository.save(
                    Subscription.builder()
                            .id(1L)
                            .plan(SubscriptionPlan.FREE)
                            .active(true)
                            .build()
            );

            subscriptionRepository.save(
                    Subscription.builder()
                            .id(2L)
                            .plan(SubscriptionPlan.PREMIUM)
                            .active(true)
                            .build()
            );

            subscriptionRepository.save(
                    Subscription.builder()
                            .id(3L)
                            .plan(SubscriptionPlan.ULTIMATE)
                            .active(true)
                            .build()
            );
        };
    }
}