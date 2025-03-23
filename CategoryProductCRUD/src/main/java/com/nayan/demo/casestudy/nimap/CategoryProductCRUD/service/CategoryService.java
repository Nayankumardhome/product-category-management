package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model.Category;
import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.repository.CategoryRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {
	
	private final CategoryRepository repository;

    public Page<Category> getAllCategories(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Integer id) {
        return repository.findById(id);
    }

    public Category createCategory(@Valid Category category, MultipartFile file) throws IOException {
        Optional<Category> existingCategoryOpt = repository.findByName(category.getName());

        if (existingCategoryOpt.isPresent()) {
            throw new IllegalStateException("Category already exists with name: " + category.getName());
        }

        String imageUrl = saveCategoryImage(file);
        category.setImageUrl(imageUrl);
        
        return repository.save(category);
    }

    public Category updateCategory(Integer id, @Valid Category category) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + id));

        existingCategory.setName(category.getName());
        return repository.save(existingCategory);
    }

    public Category updateCategoryImage(Integer id, MultipartFile file) throws IOException {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + id));

        String imageUrl = saveCategoryImage(file);
        category.setImageUrl(imageUrl);
        return repository.save(category);
    }

    public void deleteCategory(Integer id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Category not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    private String saveCategoryImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty.");
        }

        String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        File saveDir = new File("uploads/category_img");

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        Path path = Paths.get(saveDir.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/category_img/" + fileName;
    }
}
