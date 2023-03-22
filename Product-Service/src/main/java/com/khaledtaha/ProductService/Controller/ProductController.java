package com.khaledtaha.ProductService.Controller;


import com.khaledtaha.ProductService.Service.ProductService;
import com.khaledtaha.ProductService.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product request){
       return this.productService.saveProduct(request);
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product searchBy(@PathVariable("name") String name){
        return this.productService.searchBy(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") String id){
        this.productService.deleteBy(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }


}
