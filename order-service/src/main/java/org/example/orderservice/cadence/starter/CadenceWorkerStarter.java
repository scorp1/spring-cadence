package org.example.orderservice.cadence.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.cadence.config.register.CadenceWorkerRegister;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CadenceWorkerStarter implements CommandLineRunner {
  private final CadenceWorkerRegister cadenceWorkerRegister;

  @Override
  public void run(String... args) {
    try {
      cadenceWorkerRegister.register();
    } catch (Exception e) {
      log.error("Failed spring startup with cadence", e);
    }
  }
}
