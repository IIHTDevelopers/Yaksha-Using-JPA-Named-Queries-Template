package com.yaksha.assignment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Product;
import com.yaksha.assignment.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	// Create a product (POST)
	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}

	// Update a product (PUT)
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		product.setId(id); // Make sure the product's ID is set for updating
		return productRepository.save(product);
	}

	// Get all products
	@GetMapping
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// Get a product by ID
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		Optional<Product> product = productRepository.findById(id);
		return product.orElse(null); // Return null if not found
	}

	// Delete a product
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productRepository.deleteById(id);
	}

	// Get products by category
	@GetMapping("/category/{category}")
	public List<Product> getProductsByCategory(@PathVariable String category) {
		return productRepository.findByCategory(category);
	}
}
