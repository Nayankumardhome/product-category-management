package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Optional<Category> findByName(String name);
	Page<Category> findAll(Pageable pageable);
}
