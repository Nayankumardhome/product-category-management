package com.nayan.demo.casestudy.nimap.CategoryProductCRUD.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 500, name = "product_name")
	private String productName;
	
	@Column(length = 5000)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonBackReference
	private Category category;
	
	@Column(nullable = false)
	private Double price;
	private Integer stock;
	
	private String imageUrl;
	
	private Double discount;
	
	private Double finalPrice;
	
	public Integer getCategoryId() {
	    return category != null ? category.getId() : null;
	}
}
