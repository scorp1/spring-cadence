package org.example.customerservice.cadence.config;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.worker.WorkerFactory;
import java.util.UUID;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.example.customerservice.cadence.config.properties.activity.CadenceActivityOptions;
import org.example.customerservice.cadence.config.properties.workflow.CadenceWorkflowOptions;

public interface CadenceConfigClient {

  WorkerFactory getWorkerFactory() throws ConcurrentException;

  ActivityOptions getActivityOptions();

  <T> T newWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions);

  <T> T newWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions,
      UUID workflowId);

  <T> T newChildWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions);

  <T> T newChildWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions,
      UUID workflowId);

  <T> T newExternalActivityStub(Class<T> activityInterface, CadenceActivityOptions cadenceActivityOptions);

  <T> T newLocalActivityStub(Class<T> activityInterface);

  ActivityOptions createExternalActivityOptions(String taskList, CadenceActivityOptions cadenceOptions);

}
