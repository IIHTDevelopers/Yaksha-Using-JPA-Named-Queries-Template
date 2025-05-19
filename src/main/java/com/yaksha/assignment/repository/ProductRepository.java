package com.yaksha.assignment.repository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	// Use the named query from the Product entity to find products by category
}
