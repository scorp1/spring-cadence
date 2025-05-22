package org.example.orderservice.cadence.config.properties;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskListConfig {

  @Bean
  public CadenceWorkerOptions orderWorkerConfig(CadenceWorkerProperties cadenceWorkersProperties) {
    return cadenceWorkersProperties
        .getOrderWorker();
  }
}
