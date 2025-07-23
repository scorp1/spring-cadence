package org.example.orderservice.cadence.local.activity;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Customer;
import org.example.orderservice.service.CompensateRepositoryService;
import org.example.orderservice.service.CustomerRepositoryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerLocalActivityImpl implements CustomerLocalActivity{
  private final CustomerRepositoryService customerRepositoryService;
  private final CompensateRepositoryService compensateRepositoryService;

  @Override
  public Boolean chargeCustomer(Long customerId, Integer sum, UUID requestId) {
    Optional<Customer> currentCustomer = customerRepositoryService.findCustomerById(customerId);
    if (currentCustomer.isPresent() && currentCustomer.get().getMoney() >= sum) {
      var money = currentCustomer.get().getMoney() - sum;
      currentCustomer.get().setMoney(money);
      customerRepositoryService.processWallet(currentCustomer.get());
      compensateRepositoryService.saveCustomer(
          currentCustomer.get().getId(),
          currentCustomer.get().getMoney(),
          requestId);

      return true;
    }
    return false;
  }

  @Override
  public void refundCustomer(Long customerId, Integer sum) {
    Optional<Customer> currentCustomer = customerRepositoryService.findCustomerById(customerId);
    var money = currentCustomer.get().getMoney() + sum;
    currentCustomer.get().setMoney(money);
    customerRepositoryService.processWallet(currentCustomer.get());
  }
}
