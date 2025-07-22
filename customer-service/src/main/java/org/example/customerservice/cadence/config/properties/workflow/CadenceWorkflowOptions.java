package org.example.customerservice.cadence.config.properties.workflow;

import lombok.Data;

@Data
public class CadenceWorkflowOptions {

  private Integer executionTimeout = 600;
  private Integer maximumAttempts = 1;
}
