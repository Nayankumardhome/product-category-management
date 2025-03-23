package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model.Product;
import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.repository.CategoryRepository;
import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.repository.ProductRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

	private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    
    public Page<Product> getAllProducts(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }
    
    public Product getProductById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
    }
    
    public Product createProduct(@Valid Product product, MultipartFile file) throws IOException {
        Optional<Product> existingProductOpt = repository.findByProductName(product.getProductName());
        Optional<Category> ct = categoryRepository.findById(product.getCategoryId());
        if(ct.isPresent()) {
        	Category c = ct.get();
        	System.out.println(c);
        }
        if (existingProductOpt.isPresent()) {
            throw new IllegalStateException("Product already exists with name: " + product.getProductName());
        }
        
        if (!categoryRepository.existsById(product.getCategory().getId())) {
            throw new NoSuchElementException("Category not found with ID: " + product.getCategory().getId());
        }
        
        String imageUrl = saveProductImage(file);
        product.setImageUrl(imageUrl);
        product.setFinalPrice(BigDecimal.valueOf(product.getPrice() - ((product.getPrice() * product.getDiscount()) / 100))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue());
        
        return repository.save(product);
    }
    
    public Product updateProduct(Integer id, @Valid Product product) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
        
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setFinalPrice(BigDecimal.valueOf(product.getPrice() - ((product.getPrice() * product.getDiscount()) / 100))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue());
        
        Integer categoryId = product.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
        		.orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
        
        existingProduct.setCategory(category);
        
        return repository.save(existingProduct);
    }
    
    public Product updateProductImage(Integer id, MultipartFile file) throws IOException {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
        
        String imageUrl = saveProductImage(file);
        product.setImageUrl(imageUrl);
        return repository.save(product);
    }
    
    public void deleteProduct(Integer id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Product not found with ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private String saveProductImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty.");
        }
        
        String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        File saveDir = new File("uploads/product_img");
        
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        
        Path path = Paths.get(saveDir.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
        return "/uploads/product_img/" + fileName;
    }
}
