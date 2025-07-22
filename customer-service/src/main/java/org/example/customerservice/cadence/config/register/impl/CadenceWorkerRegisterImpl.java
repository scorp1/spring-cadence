package org.example.customerservice.cadence.config.register.impl;

import com.uber.cadence.internal.worker.PollerOptions;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerOptions;
import com.uber.cadence.workflow.Functions.Func;
import insure.pulse.pdp.cadencetools.common.WorkerOptionsProvider;
import insure.pulse.pdp.cadencetools.impl.converter.JacksonDataConverter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.tuple.Pair;
import org.example.customerservice.cadence.config.CadenceConfigClient;
import org.example.customerservice.cadence.config.properties.CadenceProperties;
import org.example.customerservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.customerservice.cadence.config.register.CadenceWorkerRegister;
import org.example.customerservice.cadence.config.register.workers.CadenceWorker;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class CadenceWorkerRegisterImpl implements CadenceWorkerRegister {

  private final JacksonDataConverter dataConverter;
  private final CadenceConfigClient cadenceConfigClient;
  private final CadenceProperties cadenceProperties;
  private final WorkerOptionsProvider workerOptionsProvider;

  private final List<CadenceWorker> cadenceWorkers;

  @Override
  @SneakyThrows
  public void register() {
    log.info("Register workerFactory");

    cadenceWorkers.forEach(w -> registerNewWorker(
        w.getWorkerOptions(),
        w.getWorkflows(),
        w.getActivities().toArray()
    ));

    startWorkerFactory();
  }

  private <T> Worker registerNewWorker(CadenceWorkerOptions workerOptions, Class<T> workflowInterface,
      Func<T> factory, Object... activities) {

    return registerNewWorker(workerOptions,
        Collections.singletonList(Pair.of(workflowInterface, factory)), activities);
  }

  @SuppressWarnings("unchecked")
  private <T> Worker registerNewWorker(CadenceWorkerOptions workerOptions, List<?> workflows, Object... activities) {
    Worker worker = registerNewWorker(workerOptions.getTaskList(), getWorkerOptions(workerOptions));

    workflows
        .stream()
        .map(p -> (Pair<Class<T>, Func<T>>) p)
        .forEach(p -> worker.addWorkflowImplementationFactory(p.getLeft(), p.getRight()));

    Optional.ofNullable(activities)
        .filter(ArrayUtils::isNotEmpty)
        .ifPresent(worker::registerActivitiesImplementations);

    return worker;
  }

  @SneakyThrows
  private Worker registerNewWorker(String taskList, WorkerOptions workerOptions) {
    log.info("Registering a new worker with a {}", taskList);
    WorkerFactory workerFactory = cadenceConfigClient.getWorkerFactory();
    return workerFactory.newWorker(taskList, workerOptions);
  }

  private void startWorkerFactory() throws ConcurrentException {
    cadenceConfigClient.getWorkerFactory().start();
  }

  private WorkerOptions getWorkerOptions(CadenceWorkerOptions options) {
    return workerOptionsProvider.getBuilder()
        .setMaxConcurrentActivityExecutionSize(options.getActivityPoolSize())
        .setMaxConcurrentWorkflowExecutionSize(options.getWorkflowPoolSize())
        .setActivityPollerOptions(
            PollerOptions.newBuilder()
                .setPollThreadCount(options.getActivityPollThreadCount())
                .build()
        )
        .setWorkflowPollerOptions(
            PollerOptions.newBuilder()
                .setPollThreadCount(options.getWorkflowPollThreadCount())
                .build())
        .build();
  }
}
