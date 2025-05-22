package org.example.orderservice.service.cadence;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerProperties;
import org.example.orderservice.cadence.config.properties.workflow.CadenceWorkflowProperties;
import org.example.orderservice.cadence.local.workflow.CreateOrderWorkflow;
import org.example.orderservice.cadence.config.CadenceConfigClient;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadenceWorkflowServiceImpl implements CadenceWorkflowService{

  private final CadenceConfigClient cadenceConfigClient;
  private final CadenceWorkerProperties cadenceWorkersProperties;
  private final CadenceWorkflowProperties cadenceWorkflowsProperties;

  @Override
  public CreateOrderResponseDto createOrder(CreateOrderRequestDto request) {
    String taskList = cadenceWorkersProperties.getOrderWorker().getTaskList();
    CreateOrderWorkflow workflow = cadenceConfigClient.newWorkflowStub(
        CreateOrderWorkflow.class, taskList, cadenceWorkflowsProperties.getOrderWorkflow());

    return workflow.createOrder(request);
  }
}
