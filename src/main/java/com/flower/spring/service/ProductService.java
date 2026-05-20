package com.flower.spring.service;

import com.flower.spring.Dtos.ProductRequest;
import com.flower.spring.Dtos.ProductResponse;
import com.flower.spring.config.SecurityUtils;
import com.flower.spring.model.Category;
import com.flower.spring.model.Product;
import com.flower.spring.model.User;
import com.flower.spring.repository.CategoryRepository;
import com.flower.spring.repository.ProductRepository;
import com.flower.spring.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final SecurityUtils securityUtils;

    public Product create(ProductRequest request, MultipartFile image) throws  Exception{
        User user = securityUtils.getCurrentUser();
          String imageUrl = fileService.upload(image);
         Category category = categoryRepository.findById(request.getCategoryId())
                 .orElseThrow(() -> new RuntimeException("Category not found"));
         Product product = new Product();
         product.setName(request.getName());
         product.setDescription(request.getDescription());
         product.setPrice(request.getPrice());
         product.setStock(request.getStock());
         product.setIsActive(request.getIsActive());
         product.setImageUrl(imageUrl);
         product.setCategory(category);
         product.setUser(user);
        return repository.save(product);
    }

    public Page<ProductResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = repository.findAll(pageable);

        return products.map(this::mapToResponse);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getIsActive(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getUser().getId()
        );
    }

    public ProductResponse getById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    public Page<ProductResponse> getProductByNameAndCategoryName(
            int page,
            int size,
            String proName,
            String cateName
    ) {

        Pageable pageable = PageRequest.of(page, size);

        proName = proName == null ? "" : proName;
        cateName = cateName == null ? "" : cateName;
        System.out.println("proName = " + proName);
        System.out.println("cateName = " + cateName);
        Page<Product> products =
                repository.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
                        proName,
                        cateName,
                        pageable
                );

        return products.map(this::mapToResponse);
    }

    public Product update(Integer id, ProductRequest request, MultipartFile image) throws Exception {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        User user = securityUtils.getCurrentUser();
        if(!product.getUser().getId().equals(user.getId())){ throw  new RuntimeException("Are you not permitted to modify this product");}
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        product.setIsActive(request.getIsActive());
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileService.upload(image);
            product.setImageUrl(imageUrl);
        }
        return repository.save(product);
    }

    public void delete(Integer id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        repository.delete(product);
    }

    public ApiResponse<List<ProductResponse>> getTop3Product() {
           List<Product> products = repository.findTop3ByOrderByIdDesc();
           List<ProductResponse> responses = products.stream().map(item ->  ProductResponse.builder().id(item.getId()).name(item.getName()).description(item.getDescription()).price(item.getPrice()).stock(item.getStock()).imageUrl(item.getImageUrl()).categoryName(item.getCategory().getName()).build()).toList();
        return new ApiResponse<>(
                true,
                "success",
                responses
        );
    }

    public ApiResponse<List<ProductResponse>> getProductByOwnerId(Integer Id){
        List<Product> products = repository.findByUserId(Id);
        List<ProductResponse> responses =
                products.stream().map(item ->
                        ProductResponse.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .description(item.getDescription())
                                .price(item.getPrice())
                                .stock(item.getStock())
                                .imageUrl(item.getImageUrl())
                                .isActive(item.getIsActive())
                                .categoryId(item.getId())
                                .categoryName(item.getCategory().getName())
                                .userId(item.getUser().getId())
                                .build())
                        .toList();
        return new ApiResponse<>(true, "success",responses);
    }

}