package org.example.orderservice.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;
import org.example.orderservice.dto.ProductRequestDto;
import org.example.orderservice.dto.ProductResponseDto;

public interface ProductActivityTmp {

  @ActivityMethod(name = "warehouse-service:reserveProduct")
  ProductResponseDto reserveProductTmp(ProductRequestDto request);

  @ActivityMethod(name = "warehouse-service:releaseProduct")
  void releaseProductTmp(ProductRequestDto request);
}
