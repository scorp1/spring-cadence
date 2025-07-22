package org.example.orderservice.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Compensate;
import org.example.orderservice.repository.CompensateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompensateRepositoryService {
  private final CompensateRepository compensateRepository;

  public Compensate save(Long productId, Integer productCount, UUID requestId) {
    var compensate = new Compensate();
    compensate.setRequestId(requestId);
    compensate.setProductId(productId);
    compensate.setProductCount(productCount);

    return compensateRepository.save(compensate);
  }

  public Compensate findById(UUID requestId) {
    var compensate = compensateRepository.findById(requestId);

    return compensate.get();
  }

}
