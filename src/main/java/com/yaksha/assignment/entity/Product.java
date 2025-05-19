package com.yaksha.assignment.entity;

public class Product {

	private Long id;

	private String name;
	private String description;
	private Double price;
	private String category; // Added category field for the query

	// Constructors
	public Product() {
	}

	public Product(String name, String description, Double price, String category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product{id=" + id + ", name='" + name + "', description='" + description + "', price=" + price
				+ ", category='" + category + "'}";
	}
}
