package org.example.customerservice.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.entity.Compensate;
import org.example.customerservice.repository.CompensateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompensateRepositoryService {
  private final CompensateRepository compensateRepository;

  public Compensate save(Long customerId, Integer customerMoney, UUID requestId) {
    var compensateCurrent = compensateRepository.findById(requestId);
    if (compensateCurrent.isPresent()) {
      compensateCurrent.get().setCustomerId(customerId);
      compensateCurrent.get().setCustomerMoney(customerMoney);
      return compensateRepository.save(compensateCurrent.get());
    }
    var compensate = new Compensate();
    compensate.setRequestId(requestId);
    compensate.setCustomerId(customerId);
    compensate.setCustomerMoney(customerMoney);

    return compensateRepository.save(compensate);
  }

  public Compensate findByCustomerId(UUID requestId) {
    var compensate = compensateRepository.findById(requestId);

    return compensate.get();
  }

}
