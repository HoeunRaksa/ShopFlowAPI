package com.flowShop.spring.repository;

import com.flowShop.spring.model.Order;
import com.flowShop.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyerOrderByCreateAtDesc(User buyer);
}
