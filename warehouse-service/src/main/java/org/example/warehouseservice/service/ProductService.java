package org.example.warehouseservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.warehouseservice.entity.Product;
import org.example.warehouseservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void updateCount(Product product) {
        productRepository.save(product);
    }
}
