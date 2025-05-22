package org.example.orderservice.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Product;
import org.example.orderservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRepositoryService {

  private final ProductRepository productRepository;

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  @Transactional
  public void updateCount(Product product) {
    productRepository.save(product);
  }

}
