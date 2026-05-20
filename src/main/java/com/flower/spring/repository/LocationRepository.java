package com.flower.spring.repository;

import com.flower.spring.model.Location;
import com.flower.spring.model.User;
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
