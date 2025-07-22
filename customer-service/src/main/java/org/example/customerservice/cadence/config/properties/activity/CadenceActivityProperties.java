package org.example.customerservice.cadence.config.properties.activity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.cadence.activities")
public class CadenceActivityProperties {
  private CadenceActivityOptions customerActivity = new CadenceActivityOptions();
}
