package com.flowShop.spring.service;

import com.flowShop.spring.Enum.PaymentPurpose;
import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.model.Payment;
import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.PaymentRepository;
import com.flowShop.spring.repository.UserRepository;
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

    public ResultMessage<String> paymentCallback(
            PaymentCallbackRequest request
    ) {

        Payment payment = paymentRepository.findById(request.paymentId())
                .orElse(null);

        if (payment == null) {
            return ResultMessage.error(4003, "Payment not found");
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

            SubscriptionPlan plan = switch (payment.getReferenceId().intValue()) {
                case 1 -> SubscriptionPlan.FREE;
                case 2 -> SubscriptionPlan.PREMIUM;
                case 3 -> SubscriptionPlan.ULTIMATE;
                default -> null;
            };

            if (plan == null) {
                return ResultMessage.error(4005, "Invalid subscription plan");
            }

            LocalDateTime now = LocalDateTime.now();

            user.setSubscriptionPlan(plan);
            user.setSubscriptionStartDate(now);
            user.setSubscriptionEndDate(now.plusMonths(1));

            userRepository.save(user);
        }

        return ResultMessage.success(
                1001,
                "Payment verified and subscription updated successfully",
                "SUCCESS"
        );
    }
}
