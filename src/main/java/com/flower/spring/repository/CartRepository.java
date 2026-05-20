package com.flower.spring.repository;

import com.flower.spring.model.ItemCart;
import com.flower.spring.model.Product;
import com.flower.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<ItemCart, Integer> {
              List<ItemCart> findByUser(User user);
              List<ItemCart> findByUserId(Integer userId);
}
