package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.MasterData.asJsonString;
import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yaksha.assignment.controller.ProductController;
import com.yaksha.assignment.entity.Product;
import com.yaksha.assignment.repository.ProductRepository;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository productRepository;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to check if the 'ProductController' class has @RestController annotation
	@Test
	public void testRestControllerAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkClassAnnotation(filePath, "RestController");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if 'getProduct' method has @GetMapping annotation
	@Test
	public void testGetProductAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "getProductById", "GetMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if 'createProduct' method has @PostMapping annotation
	@Test
	public void testCreateProductHasProperAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "createProduct", "PostMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if 'deleteProduct' method has @DeleteMapping annotation
	@Test
	public void testDeleteProductAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/ProductController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "deleteProduct", "DeleteMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if the Product class is annotated with @Entity
	@Test
	public void testProductEntityAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/entity/Product.java"; // Update path as needed

		// Check if the class is annotated with @Entity
		boolean result = JavaParserUtils.checkClassAnnotation(filePath, "Entity");

		// Assert the result using YakshaAssert
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check GET request for all products
	@Test
	public void testGetAllProducts() throws Exception {
		List<Product> products = List.of(new Product("Product A", "Description A", 100.0, "Electronics"),
				new Product("Product B", "Description B", 200.0, "Clothing"));

		when(this.productRepository.findAll()).thenReturn(products);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(products)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check GET request for a product by ID
	@Test
	public void testGetProductById() throws Exception {
		Product product = new Product("Product A", "Description A", 100.0, "Electronics");
		when(this.productRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(product));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(product)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check POST request to create a new product
	@Test
	public void testCreateProduct() throws Exception {
		Product product = new Product("Product X", "A cool product", 200.0, "Clothing");

		when(this.productRepository.save(any(Product.class))).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products").content(asJsonString(product))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(product)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check PUT request to update an existing product
	@Test
	public void testUpdateProduct() throws Exception {
		Product product = new Product("Updated Product", "Updated Description", 250.0, "Electronics");

		when(this.productRepository.save(any(Product.class))).thenReturn(product);
		when(this.productRepository.findById(eq(1L))).thenReturn(java.util.Optional.of(product));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/products/1").content(asJsonString(product))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(product)) ? "true" : "false"),
				businessTestFile);
	}

	// Test to check DELETE request to delete a product
	@Test
    public void testDeleteProduct() throws Exception {
        when(this.productRepository.existsById(eq(1L))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
                businessTestFile);
    }

	// Test to check GET request for products by category (using named query)
	@Test
	public void testGetProductsByCategory() throws Exception {
		List<Product> products = List.of(new Product("Product A", "Description A", 100.0, "Electronics"),
				new Product("Product C", "Description C", 150.0, "Electronics"));

		when(this.productRepository.findByCategory(eq("Electronics"))).thenReturn(products);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/category/Electronics")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(products)) ? "true" : "false"),
				businessTestFile);
	}
}
