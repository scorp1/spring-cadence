package org.example.orderservice.cadence.local.activity;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Customer;
import org.example.orderservice.service.CustomerRepositoryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerLocalActivityImpl implements CustomerLocalActivity{
  private final CustomerRepositoryService customerRepositoryService;

  @Override
  public Boolean chargeCustomer(Long customerId, Integer sum) {
    Optional<Customer> currentCustomer = customerRepositoryService.findCustomerById(customerId);
    if (currentCustomer.isPresent() && currentCustomer.get().getMoney() >= sum) {
      var money = currentCustomer.get().getMoney() - sum;
      currentCustomer.get().setMoney(money);
      customerRepositoryService.processWallet(currentCustomer.get());

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
