package org.example.orderservice.cadence.config;

import com.uber.cadence.BadRequestError;
import com.uber.cadence.DomainAlreadyExistsError;
import com.uber.cadence.EntityNotExistsError;
import com.uber.cadence.RegisterDomainRequest;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.common.RetryOptions.Builder;
import com.uber.cadence.converter.DataConverter;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerFactoryOptions;
import com.uber.cadence.workflow.ChildWorkflowOptions;
import com.uber.cadence.workflow.Workflow;
import insure.pulse.pdp.cadencetools.common.ActivityOptionsProvider;
import insure.pulse.pdp.cadencetools.common.ChildWorkflowOptionsProvider;
import insure.pulse.pdp.cadencetools.common.WorkflowClientOptionsProvider;
import insure.pulse.pdp.cadencetools.common.WorkflowOptionsProvider;
import insure.pulse.pdp.platform.common.util.exception.CommonException;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.example.orderservice.cadence.config.properties.CadenceProperties;
import org.example.orderservice.cadence.config.properties.activity.CadenceActivityOptions;
import org.example.orderservice.cadence.config.properties.workflow.CadenceWorkflowOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CadenceConfigClientImpl implements CadenceConfigClient{

  private final DataConverter dataConverter;
  private final WorkflowClientOptionsProvider workflowClientOptionsProvider;
  private final WorkflowOptionsProvider workflowOptionsProvider;
  private final ChildWorkflowOptionsProvider childWorkflowOptionsProvider;
  private final ActivityOptionsProvider activityOptionsProvider;
  private final CadenceProperties cadenceProperties;

  private final ConcurrentInitializer<WorkerFactory> workerFactory = new LazyInitializer<>() {
    @Override
    protected WorkerFactory initialize() {
      return createWorkerFactory();
    }
  };

  private final ConcurrentInitializer<WorkflowClient> workflowClient = new LazyInitializer<>() {
    @Override
    protected WorkflowClient initialize() {
      return createWorkflowClient();
    }
  };

  public synchronized WorkflowClient getWorkflowClient() throws ConcurrentException {
    return workflowClient.get();
  }

  @Override
  public synchronized WorkerFactory getWorkerFactory() throws ConcurrentException {
    return workerFactory.get();
  }

  @Override
  public ActivityOptions getActivityOptions() {
    return activityOptionsProvider.get();
  }

  @Override
  public <T> T newWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions) {
    return newWorkflowStub(workflowInterface, taskList, cadenceOptions, null);
  }

  @Override
  @SneakyThrows
  public <T> T newWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions,
      UUID workflowId) {
    WorkflowOptions.Builder workFlowOptionsBuilder = workflowOptionsProvider.getBuilder()
        .setRetryOptions(new Builder()
            .setBackoffCoefficient(2)
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(30))
            .setMaximumAttempts(cadenceOptions.getMaximumAttempts())
            .setDoNotRetry(BadRequestError.class, EntityNotExistsError.class, CommonException.class)
            .build())
        .setTaskList(taskList)
        .setExecutionStartToCloseTimeout(
            Duration.ofSeconds(cadenceOptions.getExecutionTimeout()));

    if (workflowId != null) {
      workFlowOptionsBuilder.setWorkflowId(workflowId.toString());
    }

    return getWorkflowClient().newWorkflowStub(workflowInterface, workFlowOptionsBuilder.build());
  }

  @Override
  public <T> T newChildWorkflowStub(Class<T> workflowInterface, String taskList,
      CadenceWorkflowOptions cadenceOptions) {

    return newWorkflowStub(workflowInterface, taskList, cadenceOptions, null);
  }

  @Override
  @SneakyThrows
  public <T> T newChildWorkflowStub(Class<T> workflowInterface, String taskList, CadenceWorkflowOptions cadenceOptions,
      UUID workflowId) {
    ChildWorkflowOptions.Builder workFlowOptionsBuilder = childWorkflowOptionsProvider.getBuilder()
        .setTaskList(taskList)
        .setExecutionStartToCloseTimeout(
            Duration.ofSeconds(cadenceOptions.getExecutionTimeout()));

    if (workflowId != null) {
      workFlowOptionsBuilder.setWorkflowId(workflowId.toString());
    }

    return Workflow.newChildWorkflowStub(workflowInterface, workFlowOptionsBuilder.build());
  }

  @Override
  public <T> T newExternalActivityStub(Class<T> activityInterface, CadenceActivityOptions cadenceActivityOptions) {
    return Workflow.newActivityStub(activityInterface,
        createExternalActivityOptions(Workflow.getWorkflowInfo().getTaskList(), cadenceActivityOptions));
  }

  @Override
  public <T> T newLocalActivityStub(Class<T> activityInterface) {
    return Workflow.newLocalActivityStub(activityInterface);
  }

  @Override
  public ActivityOptions createExternalActivityOptions(String taskList,
      CadenceActivityOptions cadenceOptions) {

    return activityOptionsProvider.getBuilder()
        .setTaskList(taskList)
        .setScheduleToStartTimeout(
            Duration.ofSeconds(cadenceOptions.getScheduleToStartTimeoutInSeconds()))
        .setStartToCloseTimeout(
            Duration.ofMinutes(cadenceOptions.getStartToCloseTimeoutInMinutes())).build();
  }

  private WorkflowClient createWorkflowClient() {
    IWorkflowService workflowService = getWorkflowService();
    registerCadenceDomain(workflowService);

    return WorkflowClient.newInstance(
        workflowService,
        workflowClientOptionsProvider.getBuilder()
            .setDataConverter(dataConverter)
            .setDomain(cadenceProperties.getDomain())
            .build());
  }

  @SneakyThrows
  private WorkerFactory createWorkerFactory() {

    return WorkerFactory.newInstance(
        createWorkflowClient(),
        createFactoryOptions());
  }

  private IWorkflowService getWorkflowService() {
    return new WorkflowServiceTChannel(ClientOptions.newBuilder()
        .setHost(cadenceProperties.getHost())
        .setPort(cadenceProperties.getPort())
        .build());
  }

  private WorkerFactoryOptions createFactoryOptions() {
    return WorkerFactoryOptions.newBuilder().build();
  }

  @SneakyThrows
  private void registerCadenceDomain(IWorkflowService workflowService) {
    log.debug("Trying to register domain:{} using host:{} and port:{}",
        cadenceProperties.getDomain(), cadenceProperties.getHost(), cadenceProperties.getPort());

    try {
      workflowService
          .RegisterDomain(
              new RegisterDomainRequest()
                  .setDescription(cadenceProperties.getDomainDescription())
                  .setEmitMetric(false)
                  .setName(cadenceProperties.getDomain())
                  .setWorkflowExecutionRetentionPeriodInDays(cadenceProperties.getRetentionPeriodInDays()));
      log.info("Successfully registered domain \"{}\" with retentionDays = {}",
          cadenceProperties.getDomain(),
          cadenceProperties.getRetentionPeriodInDays());
    } catch (DomainAlreadyExistsError e) {
      log.debug("Domain \"{}\" already exists", cadenceProperties.getDomain(), e);
    }
  }
}
