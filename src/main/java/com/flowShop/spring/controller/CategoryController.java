package com.flowShop.spring.controller;

import com.flowShop.spring.Dtos.CategoryRequest;
import com.flowShop.spring.Dtos.CategoryResponse;
import com.flowShop.spring.model.Category;
import com.flowShop.spring.response.ApiResponse;
import com.flowShop.spring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getCategories();
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<Category> create(@RequestPart("data") CategoryRequest request, @RequestPart("image")MultipartFile image) throws IOException {
           var saved = categoryService.create(request, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/upload-image/{id}", consumes = "multipart/form-data")
    public ApiResponse<Category> uploadImage(@PathVariable Integer id, @RequestPart("image") MultipartFile image) throws IOException{
           return categoryService.uploadImage(id,image);
    }

}
