package com.flower.spring.controller;

import com.flower.spring.Dtos.CartItemRequest;
import com.flower.spring.Dtos.CartResponse;
import com.flower.spring.response.ApiResponse;
import com.flower.spring.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping
    public ApiResponse<List<CartResponse>> getCarts() {
        return new ApiResponse<>(
                true,
                "Get products in cart",
                cartService.getCarts()
        );
    }

    @PostMapping("/create")
    public ApiResponse<CartResponse> createCart(
            @RequestBody CartItemRequest request
    ) {
       return cartService.createCart(request);

    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> remove(@PathVariable Integer id){
         return   cartService.deleteCard(id);
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateQuantity(
            @PathVariable Integer id,
            @RequestParam Integer quantity
    ) {
        return cartService.updateQuantity(id, quantity);
    }
}