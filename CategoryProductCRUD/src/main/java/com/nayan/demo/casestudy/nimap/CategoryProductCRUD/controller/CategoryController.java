package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model.Category;
import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo, 
            @RequestParam(defaultValue = "3") Integer pageSize) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNo - 1, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + id));
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,  MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Category> createCategory(
            @Valid @RequestPart("category") Category category,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(category, file));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }
    
    @PatchMapping("/{id}/image")
    public ResponseEntity<Category> updateCategoryImage(@PathVariable Integer id, @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(categoryService.updateCategoryImage(id, file));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with ID " + id + " has been successfully deleted.");
    }
}
