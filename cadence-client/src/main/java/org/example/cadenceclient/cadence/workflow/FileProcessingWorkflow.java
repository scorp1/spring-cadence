package org.example.cadenceclient.cadence.workflow;

import com.uber.cadence.workflow.QueryMethod;
import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;
import org.springframework.boot.ApplicationArguments;

import java.util.List;

public interface FileProcessingWorkflow {
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = "file-processing")
    String processFile(ApplicationArguments args);

    @QueryMethod(name="history")
    List<String> getHistory();

    @QueryMethod(name="status")
    String getStatus();

    @SignalMethod
    void retryNow();

    @SignalMethod
    void abandon();
}
