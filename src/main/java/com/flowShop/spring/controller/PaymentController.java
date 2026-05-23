package com.flowShop.spring.controller;

import com.flowShop.spring.request.PaymentCallbackRequest;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment/callback")
    public ResultMessage<String> paymentCallback(
            @RequestBody PaymentCallbackRequest request
    ) {
        return paymentService.paymentCallback(request);
    }
}
