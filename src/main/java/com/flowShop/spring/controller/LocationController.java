package com.flowShop.spring.controller;

import com.flowShop.spring.Dtos.LocationResponse;
import com.flowShop.spring.response.ApiResponse;
import com.flowShop.spring.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService service;

    @GetMapping
    public ApiResponse<List<LocationResponse>> getMyLocation() {
        return service.getMyLocation();
    }

}