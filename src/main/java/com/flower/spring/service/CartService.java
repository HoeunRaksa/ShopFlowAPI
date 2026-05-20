package com.flower.spring.service;

import com.flower.spring.Dtos.CartItemRequest;
import com.flower.spring.Dtos.CartResponse;
import com.flower.spring.config.SecurityUtils;
import com.flower.spring.model.ItemCart;
import com.flower.spring.model.Product;
import com.flower.spring.model.User;
import com.flower.spring.repository.CartRepository;
import com.flower.spring.repository.ProductRepository;
import com.flower.spring.repository.UserRepository;
import com.flower.spring.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class CartService {
    private final CartRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    public List<CartResponse> getCarts() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return repository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public ApiResponse<CartResponse> createCart(CartItemRequest request) {
        User user = securityUtils.getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ItemCart cart = ItemCart.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        ItemCart saved = repository.save(cart);

        return new ApiResponse<>(true, "cart is created successfully", CartResponse.builder()
                .id(saved.getId())
                .quantity(saved.getQuantity())
                .product(saved.getProduct())
                .build());
    }

    public ApiResponse<String> deleteCard(Integer id) {

        ItemCart cart = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        repository.delete(cart);

        return new ApiResponse<>(
                true,
                "Cart removed successfully",
                "Deleted"
        );
    }

    public ApiResponse<String> updateQuantity(Integer id, Integer quantity) {

        ItemCart cart = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.setQuantity(quantity);

        repository.save(cart);

        return new ApiResponse<>(
                true,
                "Quantity updated successfully",
                "Updated"
        );
    }

    private CartResponse toResponse(ItemCart item) {
        return CartResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .product(item.getProduct())
                .build();
    }
}
