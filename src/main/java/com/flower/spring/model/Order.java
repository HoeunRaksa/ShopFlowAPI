package com.flower.spring.model;

import com.flower.spring.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    private User buyer;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> item;
    private Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime createAt;
}
