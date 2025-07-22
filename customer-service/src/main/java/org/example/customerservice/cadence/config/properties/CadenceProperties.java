package org.example.customerservice.cadence.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.cadence")
public class CadenceProperties {

  private String domain;
  private String host;
  private Integer port;

  private String domainDescription = "Main domain";
  private int retentionPeriodInDays = 1;
}
