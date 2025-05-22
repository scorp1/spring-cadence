package org.example.orderservice.cadence.config.properties.worker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.cadence.workers")
public class CadenceWorkerProperties {

  public CadenceWorkerOptions orderWorker = new CadenceWorkerOptions();

}
