package org.example.orderservice.cadence.config.properties.workflow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.cadence.workflows")
public class CadenceWorkflowProperties {

  private CadenceWorkflowOptions orderWorkflow = new CadenceWorkflowOptions();
}
