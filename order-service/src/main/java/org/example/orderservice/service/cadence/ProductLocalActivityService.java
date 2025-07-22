package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.local.activity.ProductLocalActivity;
import org.example.orderservice.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLocalActivityService {

  public boolean buyProduct(Long productId, UUID requestId, Integer count, Saga saga) {
    ProductLocalActivity activity = Workflow.newLocalActivityStub(
        ProductLocalActivity.class);
    Boolean isBuy = activity.reserveProduct(productId, count, requestId);
    saga.addCompensation(activity::releaseProduct, productId, count, requestId);

    return isBuy;
  }

}
