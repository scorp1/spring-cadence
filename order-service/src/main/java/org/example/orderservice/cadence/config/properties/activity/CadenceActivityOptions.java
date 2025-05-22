package org.example.orderservice.cadence.config.properties.activity;

import lombok.Data;

@Data
public class CadenceActivityOptions {

  private Long scheduleToStartTimeoutInSeconds = 12L;
  private Long startToCloseTimeoutInMinutes = 5L;
}
