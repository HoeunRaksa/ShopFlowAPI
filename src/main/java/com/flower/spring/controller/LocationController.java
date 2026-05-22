package com.flower.spring.controller;

import com.flower.spring.Dtos.LocationResponse;
import com.flower.spring.response.ApiResponse;
import com.flower.spring.service.LocationService;
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