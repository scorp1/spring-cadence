package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.local.activity.ProductLocalActivity;
import org.example.orderservice.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLocalActivityService {

    private final ProductLocalActivity productLocalActivity;

    public Boolean buyProduct(ProductDto productDto, Saga saga) {
        Boolean isBuy = productLocalActivity.reserveProduct(productDto);
        saga.addCompensation(productLocalActivity::releaseProduct, productDto, productDto.getCount());
        return isBuy;
    }
}
