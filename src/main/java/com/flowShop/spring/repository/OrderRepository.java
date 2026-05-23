package com.flowShop.spring.repository;

import com.flowShop.spring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
