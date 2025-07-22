package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.activities.CustomerActivity;
import org.example.orderservice.cadence.activities.ProductActivityTmp;
import org.example.orderservice.cadence.config.CadenceClientConfig;
import org.example.orderservice.cadence.config.CadenceConfigClient;
import org.example.orderservice.cadence.config.properties.activity.CadenceActivityProperties;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CustomerRequestDto;
import org.example.orderservice.dto.CustomerResponseDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.dto.ProductRequestDto;
import org.example.orderservice.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderActivityService {
  private final CadenceConfigClient cadenceConfigClient;
  private final CadenceActivityProperties cadenceActivityProperties;

//  public ProductResponseDto reserveProductOrder(ProductRequestDto request,
//      Saga saga, CadenceWorkerOptions cadenceWorkerOptions) {
//    var taskList = cadenceWorkerOptions.getTaskList();
//    ProductActivityTmp productActivity = Workflow.newActivityStub(
//        ProductActivityTmp.class, cadenceConfigClient.createExternalActivityOptions(
//            taskList, cadenceActivityProperties.getProductActivity()));
//    saga.addCompensation(productActivity::releaseProductTmp, request);
//
//    return productActivity.reserveProductTmp(request);
//  }

//  public CustomerResponseDto chargeCustomerOrder(CustomerRequestDto request,
//      Saga saga, CadenceWorkerOptions cadenceWorkerOptions) {
//    var taskList = cadenceWorkerOptions.getTaskList();
//    CustomerActivity customerActivity = Workflow.newActivityStub(
//        CustomerActivity.class, cadenceConfigClient.createExternalActivityOptions(
//            taskList, cadenceActivityProperties.getCustomerActivity()));
//    saga.addCompensation(customerActivity::refundCustomer, request);
//
//    return customerActivity.chargeCustomer(request);
//  }

}
