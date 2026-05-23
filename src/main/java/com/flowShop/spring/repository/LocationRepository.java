package com.flowShop.spring.repository;

import com.flowShop.spring.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location>
    findByIdAndUserId(
            Integer id,
            Integer userId
    );
    List<Location> findByUserId(Integer userId);
}
