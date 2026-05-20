package com.flower.spring.controller;

import com.flower.spring.Dtos.ProductRequest;
import com.flower.spring.Dtos.ProductResponse;
import com.flower.spring.model.Product;
import com.flower.spring.response.ApiResponse;
import com.flower.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final ObjectMapper objectMapper;
    @GetMapping
    public Page<ProductResponse> productList(@RequestParam(defaultValue ="0")int page, @RequestParam(defaultValue = "10") int size) {
        return service.getAll(page, size);
    }
    @GetMapping("find-product")
    public Page<ProductResponse> getProductByNameAndCategoryName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName
    ){
        return service.getProductByNameAndCategoryName(
                page,
                size,
                productName,
                categoryName
        );
    }
    @GetMapping("get-top-3")
    public ApiResponse<List<ProductResponse>> getTop3(){
         return  service.getTop3Product();
    }

    @PostMapping(value = "create", consumes = "multipart/form-data")
    public Product createProduct(@RequestPart("data") String data, @RequestPart("image") MultipartFile image ) throws Exception{
        System.out.println("POST /api/product/create called");
        ProductRequest request = objectMapper.readValue(data, ProductRequest.class);
        return service.create(request, image);
    }

    @PutMapping(value = "update/{id}", consumes = "multipart/form-data")
    public Product updateProduct( @PathVariable Integer id, @RequestPart("data") String data,  @RequestPart(value = "image", required = false) MultipartFile image) throws Exception{
        System.out.println("Put /api/product/create called");
        ProductRequest request = objectMapper.readValue(data, ProductRequest.class);
        return service.update(id, request, image);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        return service.getById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("getProductOwner/{Id}")
    public  ApiResponse<List<ProductResponse>> getProductByOwner(@PathVariable Integer Id){
            return service.getProductByOwnerId(Id);
    }
}
