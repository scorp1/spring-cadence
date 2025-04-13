package org.example.cadenceclient;

import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import org.example.cadenceclient.cadence.workflow.FileProcessingWorkflow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.uber.cadence.internal.logging.LoggerTag.DOMAIN;
import static com.uber.cadence.internal.logging.LoggerTag.TASK_LIST;

@SpringBootApplication
public class CadenceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadenceClientApplication.class, args);
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain("test-domain").build());
        // Get worker to poll the task list.
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker("HelloWorldTaskList");
        worker.registerWorkflowImplementationTypes(GettingStarter.HelloWorldImpl.class);
        factory.start();

        FileProcessingWorkflow workflow = workflowClient.newWorkflowStub(FileProcessingWorkflow.class);

    }

}
