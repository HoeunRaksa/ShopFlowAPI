package com.flowShop.spring.service;

import com.flowShop.spring.Dtos.CartItemRequest;
import com.flowShop.spring.Dtos.CartResponse;
import com.flowShop.spring.Dtos.ProductResponse;
import com.flowShop.spring.config.SecurityUtils;
import com.flowShop.spring.model.ItemCart;
import com.flowShop.spring.model.Product;
import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.CartRepository;
import com.flowShop.spring.repository.ProductRepository;
import com.flowShop.spring.repository.UserRepository;
import com.flowShop.spring.response.ApiResponse;
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

        return new ApiResponse<>(
                true,
                "cart is created successfully",
                toResponse(saved)
        );
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
        Product product = item.getProduct();

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .isActive(product.getIsActive())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .userId(product.getUser().getId())
                .build();

        return CartResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .product(productResponse)
                .build();
    }
}
