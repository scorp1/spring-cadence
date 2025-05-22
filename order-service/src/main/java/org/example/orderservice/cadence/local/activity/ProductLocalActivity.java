package org.example.orderservice.cadence.local.activity;

import com.uber.cadence.activity.ActivityMethod;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Product;

public interface ProductLocalActivity {

  @ActivityMethod(name = "reserveProductLocal")
  Boolean reserveProduct(ProductDto product);

  @ActivityMethod(name = "releaseProductLocal")
  void releaseProduct(ProductDto product, Integer count);

}
