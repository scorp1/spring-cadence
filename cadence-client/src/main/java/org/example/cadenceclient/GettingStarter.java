package org.example.cadenceclient;

import com.uber.cadence.workflow.Workflow;
import com.uber.cadence.workflow.WorkflowMethod;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GettingStarter {
    private static Logger logger = Workflow.getLogger(GettingStarter.class);

    public interface HelloWorld {
        @WorkflowMethod
        void sayHello(String name);
    }
    public static class HelloWorldImpl implements HelloWorld {

        @Override
        public void sayHello(String name) {
            logger.info("Hello " + name + "!");
        }
    }
}
