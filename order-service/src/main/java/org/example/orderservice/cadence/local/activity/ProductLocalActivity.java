package org.example.orderservice.cadence.local.activity;

import com.uber.cadence.activity.ActivityMethod;
import java.util.UUID;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Product;

public interface ProductLocalActivity {

  @ActivityMethod(name = "reserveProductLocal")
  Boolean reserveProduct(Long productId, Integer count, UUID requestId);

  @ActivityMethod(name = "releaseProductLocal")
  void releaseProduct(Long productId, Integer count, UUID requestId);

}
