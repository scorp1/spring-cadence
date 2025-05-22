package org.example.orderservice.cadence.service;

import com.uber.cadence.RegisterDomainRequest;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerFactoryOptions;
import com.uber.cadence.worker.WorkerOptions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.example.orderservice.cadence.activities.OrderActivitiesImpl;
import org.example.orderservice.cadence.workflow.OrderWorkflow;
import org.example.orderservice.cadence.workflow.OrderWorkflowImpl;
import org.example.orderservice.service.CustomerService;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CadenceWorkerService {
//    private WorkerFactory factory;
//    private final org.example.orderservice.service.OrderService orderService;
//    private final ProductService productService;
//    private final CustomerService customerService;
//
//    public CadenceWorkerService(OrderService orderService, ProductService productService,
//        CustomerService customerService) {
//        this.orderService = orderService;
//        this.productService = productService;
//        this.customerService = customerService;
//    }
//
//    private WorkflowClient workflowClient;
//
//    @PostConstruct // Если используется Spring
//    public void startWorkers() {
//
//        RegisterDomainRequest request = new RegisterDomainRequest();
//        request.setName("test-domain");
//        request.setDescription("My local dev domain");
//        request.setWorkflowExecutionRetentionPeriodInDays(1);
//        request.setEmitMetric(false);
//
//        WorkflowServiceTChannel service = new WorkflowServiceTChannel(
//                ClientOptions.newBuilder()
//                        .setHost("localhost")
//                        .setPort(7933)
//                        .build());
//        try {
//            service.RegisterDomain(request);
//            System.out.println("Domain registered successfully");
//        } catch (TException e) {
//            System.out.println("Domain already exists");
//        }
//
////        WorkflowClientOptions clientOptions = WorkflowClientOptions.newBuilder()
////                .setDomain("test-domain")
////                .build();
//
//        // Создание клиента
//         workflowClient =
//                WorkflowClient.newInstance(
//                        new WorkflowServiceTChannel(
//                                ClientOptions.newBuilder()
//                                        .setHost("localhost")
//                                        .setPort(7933)
//                                        .build()
//                        ),
//                        WorkflowClientOptions.newBuilder().setDomain("test-domain").build());
//
//        // Настройка фабрики
//        this.factory = WorkerFactory.newInstance(workflowClient,
//                WorkerFactoryOptions.newBuilder()
//                        .setMaxWorkflowThreadCount(1000)
//                        .setStickyCacheSize(100)
//                        .setDisableStickyExecution(false)
//                        .build());
//
//        // Регистрация worker'а для обработки заказов
//        Worker worker = factory.newWorker("orderTaskList",
//                WorkerOptions.newBuilder()
//                        .setMaxConcurrentActivityExecutionSize(100)
//                        .setMaxConcurrentWorkflowExecutionSize(100)
//                        .build());
//
//        //OrderService orderService1 = new OrderService()
//
//        // Регистрация имплементаций
//        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
//        worker.registerActivitiesImplementations(new OrderActivitiesImpl(orderService, customerService, productService));
//
//        // Запуск factory
//        factory.start();
//    }
//
//    @PreDestroy
//    public void stopWorkers() {
//        if (factory != null) {
//            factory.shutdown();
//        }
//    }
//
//    public WorkflowClient getWorkflowClient() {
//        return workflowClient;
//    }
}

