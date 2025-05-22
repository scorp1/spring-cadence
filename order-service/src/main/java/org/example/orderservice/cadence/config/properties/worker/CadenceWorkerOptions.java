package org.example.orderservice.cadence.config.properties.worker;

import lombok.Data;

@Data
public class CadenceWorkerOptions {

  private String taskList;
  private Integer workflowPoolSize = 50;
  private Integer activityPoolSize = 400;

  private Integer activityPollThreadCount = 1;
  private Integer workflowPollThreadCount = 1;
}
