package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findByProductName(String productName);
	Page<Product> findAll(Pageable pageable);
}
