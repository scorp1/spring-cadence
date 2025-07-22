package org.example.customerservice.cadence.activities.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.cadence.activities.CustomerActivity;
import org.example.customerservice.dto.CustomerDto;
import org.example.customerservice.repository.CompensateRepository;
import org.example.customerservice.service.CompensateRepositoryService;
import org.example.customerservice.service.CustomerService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerActivityImpl implements CustomerActivity {

  private final CustomerService customerService;
  private final CompensateRepositoryService compensateRepositoryService;

//  public CustomerActivityImpl(CustomerService customerService) {
//    this.customerService = customerService;
//  }

  @Override
  public CustomerDto chargeCustomer(CustomerDto customerDto, UUID requestId) {
    var customerFind = customerService.findCustomerById(customerDto.getId());
    var oldMoney = customerFind.get().getMoney();
    var money = customerFind.get().getMoney() - customerDto.getMoney();
    customerFind.get().setMoney(money);

    var customer = customerService.updateCustomer(customerFind.get());
    compensateRepositoryService.save(customerFind.get().getId(), oldMoney, requestId);

    return customer;
  }

  @Override
  public CustomerDto refundCustomer(CustomerDto customerDto, UUID requestId) {
    var customerFind = customerService.findCustomerById(customerDto.getId());
    var compensate = compensateRepositoryService.findByCustomerId(requestId);

    var returnMoney = compensate.getCustomerMoney();
    customerFind.get().setMoney(returnMoney);
    var customer = customerService.updateCustomer(customerFind.get());

    return customer;
  }
}
