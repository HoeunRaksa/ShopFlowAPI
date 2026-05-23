package com.flowShop.spring.service;

import com.flowShop.spring.Dtos.CategoryRequest;
import com.flowShop.spring.Dtos.CategoryResponse;
import com.flowShop.spring.model.Category;
import com.flowShop.spring.repository.CategoryRepository;
import com.flowShop.spring.response.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class CategoryService {
private final CategoryRepository repository;
private final FileService fileService;

    public Category create(CategoryRequest request, MultipartFile image) throws IOException {
        Optional<Category> existing  = repository.findByName(request.getName());
        if (existing.isPresent()) {
            return existing.get();
        }
        String imageUrl = fileService.upload(image);

          var category = Category.builder()
                  .name(request.getName()).description(request.getDescription()).imageUrl(imageUrl).build();
      return repository.save(category);
    }

    public List<CategoryResponse> getCategories() {
        return repository.findAll()
                .stream()
                .map(c -> {
                    CategoryResponse res = new CategoryResponse();
                    res.setId(c.getId());
                    res.setName(c.getName());
                    res.setDescription(c.getDescription());
                    res.setImageUrl(c.getImageUrl());
                    return res;
                })
                .toList();
    }
    public ApiResponse<Category> uploadImage(Integer id,MultipartFile image) throws IOException{
           Optional<Category> categoryFound = repository.findById(id);
        if (categoryFound.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    "Category not found",
                    null
            );
        }
        String imageUrl = fileService.upload(image);
        Category category = categoryFound.get();
        category.setImageUrl(imageUrl);
       return new ApiResponse<> (true,"successes",repository.save(category));
    }
}
