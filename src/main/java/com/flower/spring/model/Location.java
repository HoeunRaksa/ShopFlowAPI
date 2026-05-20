package com.flower.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String receiverName;
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryNote;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    private User user;
}