package org.example.customerservice.cadence.workflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customerservice.dto.CustomerDto;
import org.example.customerservice.service.CustomerService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class UpdateCustomerWorkflowImpl implements CustomerWorkflow {
  private final CustomerService customerService;

  @Override
  public CustomerDto updateCustomer(CustomerDto request) {
    var orderResponse = customerService.updateCustomer(request);

    return orderResponse;
  }

}
