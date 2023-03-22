package com.khaledtaha.ProductService.advice;

import com.khaledtaha.ProductService.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice
public class ProductExceptionAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ProductNotFoundHandler(ProductNotFoundException e) {
        return e.getMessage();
    }
}
