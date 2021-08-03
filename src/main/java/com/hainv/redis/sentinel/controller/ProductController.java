package com.hainv.redis.sentinel.controller;

import com.hainv.redis.sentinel.domain.Product;
import com.hainv.redis.sentinel.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
