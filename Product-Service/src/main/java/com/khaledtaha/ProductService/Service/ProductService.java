package com.khaledtaha.ProductService.Service;

import com.khaledtaha.ProductService.exceptions.ProductNotFoundException;
import com.khaledtaha.ProductService.model.Product;
import com.khaledtaha.ProductService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product request){
        Product product = Product
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        Product savedProduct = this.productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return savedProduct;
    }

    public Product searchBy(String name){

        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));

        return product;
    }


    public void deleteBy(String id){
        this.productRepository.deleteById(id);
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }


}
