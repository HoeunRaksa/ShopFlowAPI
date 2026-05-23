package com.flowShop.spring.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangePasswordRequest {
       private String currentPassword;
       private String newPassword;
}
