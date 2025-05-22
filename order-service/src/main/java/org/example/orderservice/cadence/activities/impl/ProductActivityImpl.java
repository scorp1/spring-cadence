package org.example.orderservice.cadence.activities.impl;


import org.example.orderservice.cadence.activities.ProductActivityTmp;
import org.example.orderservice.dto.ProductRequestDto;
import org.example.orderservice.dto.ProductResponseDto;
import org.example.orderservice.service.ProductService;

public class ProductActivityImpl implements ProductActivityTmp {

  private final ProductService productService;

  public ProductActivityImpl(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public ProductResponseDto reserveProductTmp(ProductRequestDto request) {
    var product = productService.updateQuantityProduct(request.getProduct());
    return new ProductResponseDto(request.getRequestId(), product);
  }

  @Override
  public void releaseProductTmp(ProductRequestDto request) {
    productService.updateQuantityProduct(request.getProduct());

  }
}
