package com.khaledtaha.ProductService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledtaha.ProductService.Service.ProductService;
import com.khaledtaha.ProductService.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTests {

	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	Product product1 = Product
			.builder()
			.id("1")
			.name("P1")
			.description("Product 1")
			.price(BigDecimal.valueOf(123))
			.build();

	Product product2 = Product
			.builder()
			.id("2")
			.name("P2")
			.description("Product 2")
			.price(BigDecimal.valueOf(1234))
			.build();

	private List<Product> products = Arrays.asList(product1, product2);


	@Test
	@DisplayName("Add new product")
	void shouldSaveProduct() throws Exception {
		given(this.productService.saveProduct(this.product1))
				.willReturn(this.product1);

		this.mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.product1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(this.product1.getId())))
				.andExpect(jsonPath("$.name", is(this.product1.getName())))
				.andExpect(jsonPath("$.description",is(this.product1.getDescription())))
				.andExpect(jsonPath("$.price", is(this.product1.getPrice().intValue())));

		verify(this.productService).saveProduct(this.product1);

	}

	@Test
	@DisplayName("Search for product")
	void searchForProduct() throws Exception {
		given(this.productService.searchBy("P1")).willReturn(this.product1);
		this.mockMvc.perform(get("/api/products/search/{name}", "P1"))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.id", is(this.product1.getId())))
				.andExpect(jsonPath("$.name", is(this.product1.getName())))
				.andExpect(jsonPath("$.description",is(this.product1.getDescription())))
				.andExpect(jsonPath("$.price", is(this.product1.getPrice().intValue())));

		verify(this.productService).searchBy("P1");
	}


	@Test
	@DisplayName("Get all products")
	void getAllProducts() throws Exception {
		given(this.productService.getAllProducts()).willReturn(this.products);

		this.mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))

				.andExpect(jsonPath("$[0].id", is(this.product1.getId())))
				.andExpect(jsonPath("$[0].name", is(this.product1.getName())))
				.andExpect(jsonPath("$[0].description",is(this.product1.getDescription())))
				.andExpect(jsonPath("$[0].price", is(this.product1.getPrice().intValue())))


				.andExpect(jsonPath("$[1].id", is(this.product2.getId())))
				.andExpect(jsonPath("$[1].name", is(this.product2.getName())))
				.andExpect(jsonPath("$[1].description",is(this.product2.getDescription())))
				.andExpect(jsonPath("$[1].price", is(this.product2.getPrice().intValue())));

		verify(this.productService).getAllProducts();
	}

	@Test
	@DisplayName("Delete product")
	void deleteProduct() throws Exception {
		willDoNothing().given(this.productService).deleteBy("1");
		this.mockMvc.perform(delete("/api/products/{id}", "1"))
				.andExpect(status().isOk());

		verify(this.productService).deleteBy("1");
	}



}
