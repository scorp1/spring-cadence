package org.example.warehouseservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.warehouseservice.entity.Product;
import org.example.warehouseservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/buy")
    public ResponseEntity<Product> updateQuantityProduct(@RequestBody Product product) {
        productService.updateCount(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
