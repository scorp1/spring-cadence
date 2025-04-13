package org.example.cadenceclient.cadence.config;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import lombok.RequiredArgsConstructor;
import org.example.cadenceclient.CadenceClientApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CadenceClientConfig {

    public WorkflowClient createWorkerClient() {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain("test-domain").build());
        return workflowClient;
    }



}
