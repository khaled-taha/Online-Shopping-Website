package com.khaledtaha.ProductService;

import com.khaledtaha.ProductService.Controller.ProductController;
import com.khaledtaha.ProductService.exceptions.ProductNotFoundException;
import com.khaledtaha.ProductService.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
public class DatabaseIntegrationTests {

    @Autowired
    private ProductController productController;

    Product savedProduct = Product
            .builder()
            .name("P1")
            .description("Product 1")
            .price(BigDecimal.valueOf(123))
            .build();
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


    @Test
    @DisplayName("Add new product")
    void shouldSaveProduct(){
        Product product = this.productController.saveProduct(this.savedProduct);
        assertThat(product).isNotNull();
    }

    @Test
    @DisplayName("Search for product")
    void searchForProduct() {
        Product product = this.productController.searchBy("P1");
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(this.savedProduct.getName());
        assertThat(product.getDescription()).isEqualTo(this.savedProduct.getDescription());
        assertThat(product.getPrice()).isEqualTo(this.savedProduct.getPrice());
    }


    @Test
    @DisplayName("Get all products")
    void getAllProducts() {
        List<Product> products = this.productController.getAllProducts();
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getName()).isEqualTo(this.savedProduct.getName());
        assertThat(products.get(0).getDescription()).isEqualTo(this.savedProduct.getDescription());
        assertThat(products.get(0).getPrice()).isEqualTo(this.savedProduct.getPrice());
    }

    @Test
    @DisplayName("Delete product")
    void deleteProduct() {
        Product product = this.productController.searchBy("P1");
        this.productController.deleteById(product.getId());
        Exception exception = assertThrows(ProductNotFoundException.class, ()->
                this.productController.searchBy("P1")
        );
        assertEquals("The product with name P1 is not found.", exception.getMessage());
    }
}
