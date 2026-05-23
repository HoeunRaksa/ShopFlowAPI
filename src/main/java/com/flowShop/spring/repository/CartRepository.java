package com.flowShop.spring.repository;

import com.flowShop.spring.model.ItemCart;
import com.flowShop.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<ItemCart, Integer> {
              List<ItemCart> findByUser(User user);
              List<ItemCart> findByUserId(Integer userId);
}
