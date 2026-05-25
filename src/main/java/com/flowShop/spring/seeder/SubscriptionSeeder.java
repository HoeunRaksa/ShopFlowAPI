package com.flowShop.spring.seeder;

import com.flowShop.spring.model.SubscriptionFeature;
import com.flowShop.spring.model.SubscriptionPlanEntity;
import com.flowShop.spring.repository.SubscriptionPlanEntityRepository;
import com.flowShop.spring.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SubscriptionSeeder {
    private SubscriptionPlanEntity subscriptionPlan;
    @Bean
    CommandLineRunner seed(
            SubscriptionPlanEntityRepository repository
    ) {

        return args -> {

            if(repository.count() > 0){
                return;
            }

            SubscriptionPlanEntity free =
                    SubscriptionPlanEntity.builder()
                            .title("Free")
                            .price(0.0)
                            .description(
                                    "Basic access for normal users"
                            )
                            .active(true)
                            .build();

            free.setFeatures(List.of(
                    SubscriptionFeature.builder()
                            .feature("Browse products")
                            .subscriptionPlan(free)
                            .build(),
                    SubscriptionFeature.builder()
                            .feature("Add to cart")
                            .subscriptionPlan(free)
                            .build()
            ));
            SubscriptionPlanEntity premium =
                    SubscriptionPlanEntity.builder()
                            .title("Premium")
                            .price(4.99)
                            .description(
                                    "Best for users who want more"
                            )
                            .active(true)
                            .build();
            premium.setFeatures(List.of(
                    SubscriptionFeature.builder()
                            .feature("Priority support")
                            .subscriptionPlan(premium)
                            .build(),

                    SubscriptionFeature.builder()
                            .feature("Special benefits")
                            .subscriptionPlan(premium)
                            .build()
            ));

            SubscriptionPlanEntity ultimate =
                    SubscriptionPlanEntity.builder()
                            .title("Ultimate")
                            .price(9.99)
                            .description(
                                    "Full access"
                            )
                            .active(true)
                            .build();
            ultimate.setFeatures(List.of(
                    SubscriptionFeature.builder()
                            .feature("Highest priority support")
                            .subscriptionPlan(ultimate)
                            .build(),

                    SubscriptionFeature.builder()
                            .feature("Exclusive benefits")
                            .subscriptionPlan(ultimate)
                            .build()
            ));

            repository.save(free);
            repository.save(premium);
            repository.save(ultimate);
            System.out.println("Subscription seeded");
        };
    }
}