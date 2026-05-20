package com.flower.spring.service;
import com.flower.spring.Dtos.LocationRequest;
import com.flower.spring.Enum.OrderStatus;
import com.flower.spring.config.SecurityUtils;
import com.flower.spring.model.*;
import com.flower.spring.repository.CartRepository;
import com.flower.spring.repository.OrderRepository;
import com.flower.spring.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SecurityUtils securityUtils;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final LocationService locationService;

    public ApiResponse<String> createOrder(Integer locationId, LocationRequest request){
          User user = securityUtils.getCurrentUser();
          Location location = locationService.saveLocation(locationId, request);
          if(request.getReceiverName() == null){
              String receiverName = user.getFirstName() +" "+ user.getFirstName();
              location.setReceiverName(receiverName);
          }
          List<ItemCart> cart =  cartRepository.findByUserId(user.getId());
        if (cart.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    "Cart is empty",
                    null
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
        cartRepository.deleteAll(cart);
        return  new ApiResponse<>(true,"Order is created successfully", null);
    };

}
