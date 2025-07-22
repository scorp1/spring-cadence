package org.example.orderservice.cadence.config.properties.activity;

import lombok.Getter;
import lombok.Setter;
import org.example.orderservice.cadence.config.properties.activity.CadenceActivityOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.cadence.activities")
public class CadenceActivityProperties {

  private CadenceActivityOptions productActivityTmp = new CadenceActivityOptions();
  private CadenceActivityOptions customerActivity = new CadenceActivityOptions();
  private CadenceActivityOptions orderActivity = new CadenceActivityOptions();
}
