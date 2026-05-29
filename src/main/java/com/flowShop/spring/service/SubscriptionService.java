package com.flowShop.spring.service;

import com.flowShop.spring.Enum.PaymentPurpose;
import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.config.SecurityUtils;
import com.flowShop.spring.model.Payment;
import com.flowShop.spring.model.Subscription;
import com.flowShop.spring.model.SubscriptionPlanEntity;
import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.PaymentRepository;
import com.flowShop.spring.repository.SubscriptionPlanEntityRepository;
import com.flowShop.spring.repository.SubscriptionPlanRepository;
import com.flowShop.spring.repository.UserRepository;
import com.flowShop.spring.request.UpgradeSubscriptionRequest;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionHistoryResponse;
import com.flowShop.spring.response.UpgradeSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SecurityUtils securityUtils;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanEntityRepository subscriptionPlanEntityRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    public ResultMessage<List<Subscription>> getSub(){
        List<Subscription> subscriptions =
                subscriptionPlanRepository.findAll();

        return ResultMessage.success(
                1000,
                "Get subscriptions successfully",
                subscriptions
        );
    }

    public ResultMessage<UpgradeSubscriptionResponse> upgradeSubscription(UpgradeSubscriptionRequest request) {
        User user = securityUtils.getCurrentUser();
        if (request.subscriptionId() == null) {return ResultMessage.error(4001, "Subscription id is required");}

        SubscriptionPlanEntity planEntity = subscriptionPlanEntityRepository.findById(request.subscriptionId()).orElseThrow(() -> new RuntimeException("Plan not found"));

        double amount = planEntity.getPrice();
        String title = planEntity.getTitle();
        SubscriptionPlan plan = SubscriptionPlan.valueOf(title.toUpperCase());
        Payment payment = Payment.builder()
                .paymentCode("PAY-" + System.currentTimeMillis())
                .user(user)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .purpose(PaymentPurpose.SUBSCRIPTION)
                .referenceId(request.subscriptionId())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        paymentRepository.save(payment);
        UpgradeSubscriptionResponse response =
                UpgradeSubscriptionResponse.builder()
                        .paymentId(payment.getId())
                        .paymentCode(payment.getPaymentCode())
                        .subscriptionId(request.subscriptionId())
                        .plan(plan)
                        .amount(amount)
                        .paymentStatus(payment.getStatus())
                        .build();
        return ResultMessage.success(
                1000,
                "Payment created successfully. Waiting for payment confirmation.",
                response
        );
    }

    public ResultMessage<List<SubscriptionHistoryResponse>> getSubscriptionHistory() {
        User user = securityUtils.getCurrentUser();
        List<Subscription> subscriptions = subscriptionPlanRepository.findByUserOrderByStartDateDesc(user);

        List<SubscriptionHistoryResponse> response = subscriptions.stream()
                .map(sub -> SubscriptionHistoryResponse.builder()
                        .id(sub.getId())
                        .plan(sub.getPlan())
                        .startDate(sub.getStartDate())
                        .endDate(sub.getEndDate())
                        .active(sub.getActive())
                        .build())
                .collect(Collectors.toList());

        return ResultMessage.success(1003, "Subscription history retrieved successfully", response);
    }
}
