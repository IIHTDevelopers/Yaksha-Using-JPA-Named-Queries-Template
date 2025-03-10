package com.yaksha.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yaksha.assignment.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// Use the named query from the Product entity to find products by category
	List<Product> findByCategory(String category);
}
