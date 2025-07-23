package org.example.orderservice.cadence.local.activity;

import com.uber.cadence.activity.ActivityMethod;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Product;
import org.example.orderservice.service.CompensateRepositoryService;
import org.example.orderservice.service.ProductRepositoryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLocalActivityImpl implements ProductLocalActivity{
  private final ProductRepositoryService repositoryService;
  private final CompensateRepositoryService compensateRepositoryService;

  @Override
  public Boolean reserveProduct(Long productId, Integer sum, UUID requestId) {
    Optional<Product> currentProduct = repositoryService.findById(productId);
    if (currentProduct.isPresent() && currentProduct.get().getCount() >= productId) {
      var oldCount = currentProduct.get().getCount();
      var newCount = currentProduct.get().getCount() - sum;
      currentProduct.get().setCount(newCount);
      repositoryService.updateCount(currentProduct.get());
      compensateRepositoryService.saveProduct(currentProduct.get().getId(), oldCount, requestId);

      return true;
    }

    return false;
  }

  @Override
  public void releaseProduct(Long productId, Integer count, UUID requestId) {
    Optional<Product> currentProduct = repositoryService.findById(productId);
    var compensate = compensateRepositoryService.findById(requestId);
    if (compensate.isPresent()) {
      currentProduct.get().setCount(compensate.get().getProductCount());
      repositoryService.updateCount(currentProduct.get());
    }
  }

}
