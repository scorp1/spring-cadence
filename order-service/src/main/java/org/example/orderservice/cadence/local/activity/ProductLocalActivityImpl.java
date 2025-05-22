package org.example.orderservice.cadence.local.activity;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Product;
import org.example.orderservice.service.ProductRepositoryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProductLocalActivityImpl implements ProductLocalActivity{
  private final ProductRepositoryService repositoryService;

  @Transactional
  public Boolean reserveProduct(ProductDto productDto) {
    Optional<Product> currentProduct = repositoryService.findById(productDto.getId());
    if (currentProduct.isPresent() && currentProduct.get().getCount() > productDto.getCount()) {
      var newCount = currentProduct.get().getCount() - productDto.getCount();
      currentProduct.get().setCount(newCount);
      repositoryService.updateCount(currentProduct.get());

      return true;
    }

    return false;
  }

  @Transactional
  public void releaseProduct(ProductDto productDto, Integer count) {
    Optional<Product> currentProduct = repositoryService.findById(productDto.getId());
    Product product = new Product(productDto.getId(), productDto.getName(),
        productDto.getCatalogue(), productDto.getWarehouseId(), productDto.getCount() + count);
    repositoryService.updateCount(product);
  }

}
