package org.example.orderservice.cadence.service;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import org.example.orderservice.cadence.workflow.OrderWorkflow;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CadenceOrderService {
    //private final WorkflowClient workflowClient;
    private final CadenceWorkerService service;

    public CadenceOrderService(CadenceWorkerService cadenceWorkerService) {
        //this.workflowClient = cadenceWorkerService.getWorkflowClient();
        this.service = cadenceWorkerService;
    }

//    public OrderResponseDto createOrderCadence(OrderDto order) {
//
//        WorkflowOptions options = new WorkflowOptions.Builder()
//                .setTaskList("orderTaskList")
//                .setExecutionStartToCloseTimeout(Duration.ofMinutes(10))
//                .build();
//
//        OrderWorkflow orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow.class, options);
//
//        return orderWorkflow.createOrder(order);
//    }

}
