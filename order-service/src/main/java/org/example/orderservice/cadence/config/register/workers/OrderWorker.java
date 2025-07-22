package org.example.orderservice.cadence.config.register.workers;

import com.uber.cadence.workflow.Functions.Func;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerProperties;
import org.example.orderservice.cadence.local.activity.CustomerLocalActivity;
import org.example.orderservice.cadence.local.activity.ProductLocalActivity;
import org.example.orderservice.cadence.workflow.OrderWorkflowImpl;
import org.example.orderservice.cadence.workflow.CreateOrderWorkflowImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderWorker implements CadenceWorker {

  private final CadenceWorkerProperties cadenceWorkersProperties;
  private final ObjectProvider<OrderWorkflowImpl> orderWorkflow;
  private final ObjectProvider<CreateOrderWorkflowImpl> createOrderWorkflow;
  private final ProductLocalActivity productLocalActivity;
  private final CustomerLocalActivity customerLocalActivity;

  @Override
  public CadenceWorkerOptions getWorkerOptions() {
    return cadenceWorkersProperties.getOrderWorker();
  }

  @Override
  public List<Pair<Class<?>, Func<?>>> getWorkflows() {
    return List.of(
        //Pair.of(OrderWorkflowImpl.class, (Func<?>) orderWorkflow::getObject),
        Pair.of(CreateOrderWorkflowImpl.class, (Func<?>) createOrderWorkflow::getObject)
    );
  }

  @Override
  public List<?> getActivities() {
    return Arrays.asList(productLocalActivity, customerLocalActivity);
  }
}
