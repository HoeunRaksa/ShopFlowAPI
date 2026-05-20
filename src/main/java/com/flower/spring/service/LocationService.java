package com.flower.spring.service;

import com.flower.spring.Dtos.LocationRequest;
import com.flower.spring.Dtos.LocationResponse;
import com.flower.spring.config.SecurityUtils;
import com.flower.spring.model.Location;
import com.flower.spring.model.User;
import com.flower.spring.repository.LocationRepository;
import com.flower.spring.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

         private final SecurityUtils securityUtils;
         private final LocationRepository locationRepository;
    public Location saveLocation(
            Integer id,
            LocationRequest request
    ){

        User user = securityUtils.getCurrentUser();

        if(id != null){

            return locationRepository
                    .findByIdAndUserId(
                            id,
                            user.getId()
                    )
                    .orElseThrow();
        }

        if(request == null){
            throw new RuntimeException(
                    "Location data required"
            );
        }

        Location location =
                Location.builder()
                        .receiverName(
                                request.getReceiverName()
                        )
                        .phoneNumber(
                                request.getPhoneNumber()
                        )
                        .deliveryAddress(
                                request.getDeliveryAddress()
                        )
                        .deliveryNote(
                                request.getDeliveryNote()
                        )
                        .user(user)
                        .build();

        return locationRepository.save(location);
    }

    private LocationResponse mapToResponse(
            Location location
    ){
        return LocationResponse.builder()
                .id(location.getId())
                .receiverName(
                        location.getReceiverName()
                )
                .phoneNumber(
                        location.getPhoneNumber()
                )
                .deliveryAddress(
                        location.getDeliveryAddress()
                )
                .deliveryNote(
                        location.getDeliveryNote()
                )

                .userId(
                        location.getUser().getId()
                )

                .userName(
                        location.getUser().getFirstName()
                )

                .userEmail(
                        location.getUser().getEmail()
                )

                .build();
    }

    public ApiResponse<List<LocationResponse>> getMyLocation(){
        User user = securityUtils.getCurrentUser();
        List<Location> locations = locationRepository.findByUserId(user.getId());
        List<LocationResponse> responses = locations.stream().map(this::mapToResponse).toList();
        return new ApiResponse<>(true, "success", responses);
    }

}
