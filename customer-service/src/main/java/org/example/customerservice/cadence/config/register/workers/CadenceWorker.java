package org.example.customerservice.cadence.config.register.workers;

import com.uber.cadence.workflow.Functions.Func;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.example.customerservice.cadence.config.properties.worker.CadenceWorkerOptions;

public interface CadenceWorker {

  CadenceWorkerOptions getWorkerOptions();

  List<Pair<Class<?>, Func<?>>> getWorkflows();

  List<?> getActivities();
}
