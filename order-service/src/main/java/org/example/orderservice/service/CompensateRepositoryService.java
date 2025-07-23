package org.example.orderservice.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Compensate;
import org.example.orderservice.repository.CompensateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompensateRepositoryService {
  private final CompensateRepository compensateRepository;

  public Compensate saveProduct(Long productId, Integer productCount, UUID requestId) {
    var compensate = new Compensate();
    compensate.setRequestId(requestId);
    compensate.setProductId(productId);
    compensate.setProductCount(productCount);

    return compensateRepository.save(compensate);
  }

  public Compensate saveCustomer(Long customerId, Integer sum, UUID requestId) {

    var compensateFind = findById(requestId);
    if (compensateFind.isPresent()) {
      compensateFind.get().setRequestId(requestId);
      compensateFind.get().setCustomerId(customerId);
      compensateFind.get().setCustomerMoney(sum);

      return compensateRepository.save(compensateFind.get());
    }
    var compensate = new Compensate();
    compensate.setRequestId(requestId);
    compensate.setCustomerId(customerId);
    compensate.setCustomerMoney(sum);

    return compensateRepository.save(compensate);
  }

  public Optional<Compensate> findById(UUID requestId) {

    return compensateRepository.findById(requestId);
  }

  public void deleteById(UUID requestId) {
    compensateRepository.deleteById(requestId);
  }

}
