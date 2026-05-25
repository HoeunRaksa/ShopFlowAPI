package com.flowShop.spring.service;
import com.flowShop.spring.Dtos.LocationRequest;
import com.flowShop.spring.Dtos.ProductResponse;
import com.flowShop.spring.Enum.OrderStatus;
import com.flowShop.spring.config.SecurityUtils;
import com.flowShop.spring.model.*;
import com.flowShop.spring.repository.CartRepository;
import com.flowShop.spring.repository.OrderRepository;
import com.flowShop.spring.Enum.PaymentPurpose;
import com.flowShop.spring.Enum.PaymentStatus;
import com.flowShop.spring.repository.PaymentRepository;
import com.flowShop.spring.response.OrderItemResponse;
import com.flowShop.spring.response.OrderHistoryResponse;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.CreateOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SecurityUtils securityUtils;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final LocationService locationService;
    private final PaymentRepository paymentRepository;

    public ResultMessage<CreateOrderResponse> createOrder(Integer locationId, LocationRequest request){
          User user = securityUtils.getCurrentUser();
          Location location = locationService.saveLocation(locationId, request);
          if(location.getReceiverName() == null){
              String receiverName = user.getFirstName() +" "+ user.getFirstName();
              location.setReceiverName(receiverName);
          }
          List<ItemCart> cart =  cartRepository.findByUserId(user.getId());
        if (cart.isEmpty()) {
            return ResultMessage.error(
                    4006,
                    "Cart is empty"
            );
        }
        Order order = Order.builder()
                .buyer(user)
                .status(OrderStatus.PENDING)
                .location(location)
                .createAt(LocalDateTime.now())
                .build();
        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for(ItemCart itemCart : cart){
            Product product = itemCart.getProduct();
            double total = product.getPrice() * itemCart.getQuantity();
            totalPrice += total;
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemCart.getQuantity())
                    .price(total)
                    .build();
            orderItems.add(orderItem);
        }
        order.setItem(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        Payment payment = Payment.builder()
                .paymentCode("PAY-" + System.currentTimeMillis())
                .user(user)
                .amount(totalPrice)
                .status(PaymentStatus.PENDING)
                .purpose(PaymentPurpose.ORDER)
                .referenceId(order.getId().longValue())
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        CreateOrderResponse response = CreateOrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(totalPrice)
                .orderStatus(order.getStatus())
                .paymentId(payment.getId())
                .paymentCode(payment.getPaymentCode())
                .paymentStatus(payment.getStatus())
                .createdAt(order.getCreateAt())
                .build();

        return ResultMessage.success(1000, "Order created successfully. Waiting for payment.", response);
    }

    public ResultMessage<List<OrderHistoryResponse>> getOrderHistory() {
        User user = securityUtils.getCurrentUser();
        List<Order> orders = orderRepository.findByBuyerOrderByCreateAtDesc(user);

        List<OrderHistoryResponse> response = orders.stream()
                .map(order -> OrderHistoryResponse.builder()
                        .id(order.getId())
                        .totalPrice(order.getTotalPrice())
                        .status(order.getStatus())
                        .createAt(order.getCreateAt())
                        .items(order.getItem().stream()
                                .map(item -> OrderItemResponse.builder()
                                        .id(item.getId())
                                        .product(ProductResponse.builder()
                                                .id(item.getProduct().getId())
                                                .name(item.getProduct().getName())
                                                .price(item.getProduct().getPrice())
                                                .imageUrl(item.getProduct().getImageUrl())
                                                .build())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return ResultMessage.success(1002, "Order history retrieved successfully", response);
    }

}
