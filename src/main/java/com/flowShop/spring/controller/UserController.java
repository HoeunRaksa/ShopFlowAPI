package com.flowShop.spring.controller;

import com.flowShop.spring.Dtos.ChangePasswordRequest;
import com.flowShop.spring.Dtos.UserResponse;
import com.flowShop.spring.Dtos.User_Request_Contact;
import com.flowShop.spring.request.PlanRequest;
import com.flowShop.spring.request.UpgradeSubscriptionRequest;
import com.flowShop.spring.response.ApiResponse;
import com.flowShop.spring.response.ResultMessage;
import com.flowShop.spring.response.SubscriptionHistoryResponse;
import com.flowShop.spring.response.UpgradePlanResponse;
import com.flowShop.spring.response.UpgradeSubscriptionResponse;
import com.flowShop.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;

    @PutMapping(value = "/set_profile", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestPart("image") MultipartFile file) throws IOException {
        String imageUrl = userService.uploadUserImage(file);
        return ResponseEntity.ok(
                Map.of("imageUrl", imageUrl)
        );
    }
     @GetMapping("/me")
    public ResponseEntity<UserResponse> me(){
         return  ResponseEntity.ok(userService.getMe());
     }
    @PatchMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request){
           return userService.changePassword(request.getCurrentPassword(), request.getNewPassword());
    }

    @PutMapping("/create-contact")
    public ApiResponse<String> createContact(@RequestBody User_Request_Contact requestContact){
        return userService.createContact(requestContact);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Integer id){
        return userService.getUserOwnerProduct(id);
    }

}
