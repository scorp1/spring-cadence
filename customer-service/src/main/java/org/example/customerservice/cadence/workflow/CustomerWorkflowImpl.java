package org.example.customerservice.cadence.workflow;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.workflow.Workflow;
import java.time.Duration;
import org.example.customerservice.cadence.activities.CustomerActivity;
import org.example.customerservice.dto.CustomerDto;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerWorkflowImpl implements CustomerWorkflow {
    private final CustomerActivity activities = Workflow.newActivityStub(
            CustomerActivity.class,
            new ActivityOptions.Builder()
                    .setScheduleToCloseTimeout(Duration.ofMinutes(5))
                    .build());
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {

        return new CustomerDto();
    }
}
