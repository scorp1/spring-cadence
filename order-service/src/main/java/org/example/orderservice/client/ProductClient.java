package org.example.orderservice.client;

import org.example.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productService", url = "http://localhost:8083/api/products/")
public interface ProductClient {

    @GetMapping("{id}")
    public ProductDto getProductById(@PathVariable("id") Long id);

    @PostMapping("buy")
    public ProductDto updateQuantityProduct(@RequestBody ProductDto productDto);
}
