package org.example.orderservice.service;

import org.example.orderservice.client.ProductClient;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public ProductDto getProduct(Long id) {
        return productClient.getProductById(id);
    }

    public ProductDto updateQuantityProduct(ProductDto product) {
        return productClient.updateQuantityProduct(product);
    }
}
