package com.flowShop.spring.service;

import com.flowShop.spring.Dtos.UserResponse;
import com.flowShop.spring.Dtos.User_Request_Contact;
import com.flowShop.spring.Enum.PaymentPurpose;
import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.config.SecurityUtils;
import com.flowShop.spring.model.Payment;
import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.PaymentRepository;
import com.flowShop.spring.repository.UserRepository;
import com.flowShop.spring.request.PlanRequest;
import com.flowShop.spring.request.UpgradeSubscriptionRequest;
import com.flowShop.spring.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepository paymentRepository;

    public String uploadUserImage(MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String imageUrl = fileService.upload(file);
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return imageUrl;
    }

    public ApiResponse<String> createContact(User_Request_Contact requestContact){
           User user =  securityUtils.getCurrentUser();
           user.setFacebookLink(requestContact.getFacebookLink());
           user.setTelegramLink(requestContact.getTelegramLink());
           user.setPhoneNumber(requestContact.getPhoneNumber());
           userRepository.save(user);
           return new ApiResponse<>(true, "user contact information is created successfully", "");
    }


    public UserResponse getMe() {

        User user = securityUtils.getCurrentUser();

        SubscriptionPlan subscriptionPlan =
                user.getSubscriptionPlan() == null
                        ? SubscriptionPlan.FREE
                        : user.getSubscriptionPlan();

        String displayName = switch (subscriptionPlan) {
            case PREMIUM -> "Premium Member";
            case ULTIMATE -> "Ultimate Member";
            default -> "Free Member";
        };

        SubscriptionResponse subscription =
                SubscriptionResponse.builder()
                        .plan(subscriptionPlan.name())
                        .displayName(displayName)
                        .startDate(user.getSubscriptionStartDate())
                        .endDate(user.getSubscriptionEndDate())
                        .active(
                                user.getSubscriptionEndDate() == null
                                        || user.getSubscriptionEndDate().isAfter(LocalDateTime.now())
                        )
                        .build();

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .facebookLink(user.getFacebookLink())
                .telegramLink(user.getTelegramLink())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .subscription(subscription)
                .build();
    }

    public ApiResponse<String> changePassword(String currentPassword, String newPassword){
        User user = securityUtils.getCurrentUser();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ApiResponse<>(true, "password is changed successfully", "");
    }
     public ApiResponse<UserResponse> getUserOwnerProduct(Integer Id){
           User user = userRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not found owner user"));
         String role = switch (user.getRole()) {
             case PREMIUM -> "Premium Member";
             case ULTIMATE -> "Ultimate";
             default -> "Free";
         };
           UserResponse response = UserResponse.builder()
                   .id(user.getId())
                   .email(user.getEmail())
                   .firstName(user.getFirstName())
                   .lastName(user.getLastName())
                   .imageUrl(user.getImageUrl())
                   .facebookLink(user.getFacebookLink())
                   .telegramLink(user.getTelegramLink())
                   .phoneNumber(user.getPhoneNumber())
                   .role(role)
                   .build();
           return new ApiResponse<UserResponse>(true, "success", response);
     };

    public ResultMessage<UpgradePlanResponse> upgradePlan(
            PlanRequest request
    ) {

        User user = securityUtils.getCurrentUser();

        if (request.id() == null) {
            return ResultMessage.error(4001, "Plan id is required");
        }

        LocalDateTime now = LocalDateTime.now();

        SubscriptionPlan subscriptionPlan;

        switch (request.id().intValue()) {

            case 1:
                subscriptionPlan = SubscriptionPlan.FREE;
                break;

            case 2:
                subscriptionPlan = SubscriptionPlan.PREMIUM;
                break;

            case 3:
                subscriptionPlan = SubscriptionPlan.ULTIMATE;
                break;

            default:
                return ResultMessage.error(
                        4001,
                        "Invalid subscription plan id"
                );
        }

        user.setSubscriptionPlan(subscriptionPlan);
        user.setSubscriptionStartDate(now);
        user.setSubscriptionEndDate(now.plusMonths(1));

        userRepository.save(user);

        String planName = switch (subscriptionPlan) {
            case PREMIUM -> "Premium Member";
            case ULTIMATE -> "Ultimate Member";
            default -> "Free Member";
        };

        UpgradePlanResponse response = UpgradePlanResponse.builder()
                .userId(user.getId().longValue())
                .plan(subscriptionPlan)
                .planName(planName)
                .startDate(user.getSubscriptionStartDate())
                .endDate(user.getSubscriptionEndDate())
                .active(true)
                .build();

        return ResultMessage.success(
                0,
                "Success",
                response
        );
    }

    public ResultMessage<UpgradeSubscriptionResponse> upgradeSubscription(
            UpgradeSubscriptionRequest request
    ) {

        User user = securityUtils.getCurrentUser();

        if (request.subscriptionId() == null) {
            return ResultMessage.error(4001, "Subscription id is required");
        }

        SubscriptionPlan plan;
        Double amount;

        switch (request.subscriptionId().intValue()) {
            case 1:
                plan = SubscriptionPlan.FREE;
                amount = 0.0;
                break;

            case 2:
                plan = SubscriptionPlan.PREMIUM;
                amount = 9.99;
                break;

            case 3:
                plan = SubscriptionPlan.ULTIMATE;
                amount = 19.99;
                break;

            default:
                return ResultMessage.error(4002, "Invalid subscription id");
        }

        Payment payment = Payment.builder()
                .paymentCode("PAY-" + System.currentTimeMillis())
                .user(user)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .purpose(PaymentPurpose.SUBSCRIPTION)
                .referenceId(request.subscriptionId())
                .createdAt(LocalDateTime.now())
                .build();

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
}