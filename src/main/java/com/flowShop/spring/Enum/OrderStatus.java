package com.flowShop.spring.Enum;

public enum OrderStatus {

    PENDING,      // Client created order, waiting payment
    PAID,         // Payment completed
    PROCESSING,   // Seller preparing item
    SHIPPING,     // Delivering
    DELIVERED,    // Customer received
    CANCELLED     // Order cancelled

}