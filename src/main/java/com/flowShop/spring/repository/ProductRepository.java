package com.flowShop.spring.repository;

import com.flowShop.spring.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Page<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
            String proName,
            String cateName,
            Pageable pageable
    );
    List<Product> findTop3ByOrderByIdDesc();
    List<Product> findByUserId(Integer Id);
}
