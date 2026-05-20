package com.flower.spring.service;

import com.flower.spring.Dtos.UserResponse;
import com.flower.spring.Dtos.User_Request_Contact;
import com.flower.spring.config.SecurityUtils;
import com.flower.spring.model.User;
import com.flower.spring.repository.UserRepository;
import com.flower.spring.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;

    public String uploadUserImage(MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String imageUrl = fileService.upload(file);
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return imageUrl;
    }

    public ApiResponse<String> createContact(User_Request_Contact requestContact){
           User user =  securityUtils.getCurrentUser();
           user.setFacebookLink(requestContact.getFacebookLink());
           user.setTelegramLink(requestContact.getTelegramLink());
           user.setPhoneNumber(requestContact.getPhoneNumber());
           userRepository.save(user);
           return new ApiResponse<>(true, "user contact information is created successfully", "");
    }


    public UserResponse getMe() {

          User user = securityUtils.getCurrentUser();

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .facebookLink(user.getFacebookLink())
                .telegramLink(user.getTelegramLink())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public ApiResponse<String> changePassword(String currentPassword, String newPassword){
        User user = securityUtils.getCurrentUser();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ApiResponse<>(true, "password is changed successfully", "");
    }
     public ApiResponse<UserResponse> getUserOwnerProduct(Integer Id){
           User user = userRepository.findById(Id).orElseThrow(() -> new RuntimeException("Not found owner user"));
           UserResponse response = UserResponse.builder()
                   .id(user.getId())
                   .email(user.getEmail())
                   .firstName(user.getFirstName())
                   .lastName(user.getLastName())
                   .imageUrl(user.getImageUrl())
                   .facebookLink(user.getFacebookLink())
                   .telegramLink(user.getTelegramLink())
                   .phoneNumber(user.getPhoneNumber())
                   .build();
           return new ApiResponse<UserResponse>(true, "success", response);
     };
}