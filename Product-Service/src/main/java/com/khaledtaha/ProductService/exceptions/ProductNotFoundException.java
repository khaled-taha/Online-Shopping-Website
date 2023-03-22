package com.khaledtaha.ProductService.exceptions;

public class ProductNotFoundException extends RuntimeException{
    private String name;

    public ProductNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("The product with name %s is not found.", name);
    }
}
