package org.example.orderservice.cadence.config.register;

import com.uber.cadence.context.ContextPropagator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropagatorsMonitor {

//  private final List<ContextPropagator> contextPropagators;
//
//  @EventListener(ApplicationReadyEvent.class)
//  public void printPropagators() {
//    log.info("context loaded with propagators: {}", contextPropagators);
//  }
}
