package org.example.customerservice.cadence.config.register.workers;

import com.uber.cadence.workflow.Functions.Func;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.example.customerservice.cadence.activities.CustomerActivity;
import org.example.customerservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.customerservice.cadence.config.properties.worker.CadenceWorkerProperties;
import org.example.customerservice.cadence.workflow.CustomerWorkflowImpl;
import org.example.customerservice.cadence.workflow.UpdateCustomerWorkflowImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderWorker implements CadenceWorker {

  private final CadenceWorkerProperties cadenceWorkersProperties;
  private final ObjectProvider<CustomerWorkflowImpl> orderWorkflow;
  private final ObjectProvider<UpdateCustomerWorkflowImpl> updateCustomerWorkflows;
  private final CustomerActivity customerLocalActivity;

  @Override
  public CadenceWorkerOptions getWorkerOptions() {
    return cadenceWorkersProperties.getOrderWorker();
  }

  @Override
  public List<Pair<Class<?>, Func<?>>> getWorkflows() {
    return List.of(
        //Pair.of(OrderWorkflowImpl.class, (Func<?>) orderWorkflow::getObject),
        Pair.of(UpdateCustomerWorkflowImpl.class, (Func<?>) updateCustomerWorkflows::getObject)
    );
  }

  @Override
  public List<?> getActivities() {
    return Arrays.asList(customerLocalActivity);
  }
}
