package com.flowShop.spring.service;

import com.flowShop.spring.Enum.PaymentPurpose;
import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.Enum.OrderStatus;
import com.flowShop.spring.model.*;
import com.flowShop.spring.repository.*;
import com.flowShop.spring.request.PaymentCallbackRequest;
import com.flowShop.spring.response.ResultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private  final SubscriptionPlanEntityRepository subscriptionPlanEntityRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    public ResultMessage<String> paymentCallback(
            PaymentCallbackRequest request
    ) {
        Payment payment = paymentRepository.findById(request.paymentId())
                .orElse(null);
        if (payment == null) {
            return ResultMessage.error(4003, "Payment not found");
        }
        if (payment.getStatus() == PaymentStatus.PAID) {
            return ResultMessage.success(
                    1002,
                    "Payment already processed",
                    "SUCCESS"
            );
        }
        if (!"PAID".equalsIgnoreCase(request.paymentStatus())) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            return ResultMessage.error(4004, "Payment failed");
        }
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);
        if (payment.getPurpose() == PaymentPurpose.SUBSCRIPTION) {

            User user = payment.getUser();

            SubscriptionPlanEntity subscriptionPlanEntity = subscriptionPlanEntityRepository.getReferenceById(payment.getReferenceId());

            SubscriptionPlan plan = SubscriptionPlan.valueOf(subscriptionPlanEntity.getTitle().toUpperCase());

            LocalDateTime now = LocalDateTime.now();

            Subscription subscription = Subscription.builder()
                    .plan(plan)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusMonths(1))
                    .user(user)
                    .active(true).build();

            user.setSubscriptionPlan(plan);
            user.setSubscriptionStartDate(now);
            user.setSubscriptionEndDate(now.plusMonths(1));
            subscriptionPlanRepository.save(subscription);
            userRepository.save(user);
        }
        if (payment.getPurpose() == PaymentPurpose.ORDER) {
            Order order = orderRepository.findById(payment.getReferenceId().intValue())
                    .orElse(null);
            if (order != null) {
                order.setStatus(OrderStatus.PAID);
                orderRepository.save(order);
                var cartItems = cartRepository.findByUserId(payment.getUser().getId());
                cartRepository.deleteAll(cartItems);
            }
        }

        return ResultMessage.success(
                1001,
                "Payment verified and subscription updated successfully",
                "SUCCESS"
        );
    }
}
