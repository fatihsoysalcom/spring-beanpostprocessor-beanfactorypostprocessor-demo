///usr/bin/env jbang "$0" "$@"
//DEPS org.springframework.boot:spring-boot-starter:3.2.5

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class BeanProcessorsDemoApplication {

    public static void main(String[] args) {
        // Start the Spring Boot application and get the application context.
        ApplicationContext context = SpringApplication.run(BeanProcessorsDemoApplication.class, args);

        // Retrieve the MyService bean to trigger its creation and lifecycle.
        MyService myService = context.getBean(MyService.class);
        myService.doSomething();

        // Exit the application gracefully, triggering @PreDestroy.
        SpringApplication.exit(context, () -> 0);
    }

    /**
     * A simple Spring-managed bean to demonstrate the lifecycle callbacks.
     */
    @Component
    public static class MyService {
        private String message = "Default Message";

        public MyService() {
            // 1. Constructor is called when the bean is instantiated.
            System.out.println("1. MyService constructor called.");
        }

        @PostConstruct
        public void postConstruct() {
            // 3. @PostConstruct is called after properties are set and before initialization.
            System.out.println("3. MyService @PostConstruct called. Message: " + message);
        }

        public void doSomething() {
            // 5. Normal bean method execution.
            System.out.println("5. MyService doSomething() called. Final Message: " + message);
        }

        @PreDestroy
        public void preDestroy() {
            // 6. @PreDestroy is called before the bean is destroyed.
            System.out.println("6. MyService @PreDestroy called.");
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Custom BeanPostProcessor to intervene in the bean's lifecycle.
     */
    @Component
    public static class CustomBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            // 2. Called *before* any bean initialization callbacks (like @PostConstruct).
            if (bean instanceof MyService) {
                System.out.println("2. BeanPostProcessor: postProcessBeforeInitialization for " + beanName);
                ((MyService) bean).setMessage("Message set by BeanPostProcessor (before init)");
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            // 4. Called *after* any bean initialization callbacks (like @PostConstruct).
            if (bean instanceof MyService) {
                System.out.println("4. BeanPostProcessor: postProcessAfterInitialization for " + beanName);
                ((MyService) bean).setMessage("Message set by BeanPostProcessor (after init)");
            }
            return bean;
        }
    }

    /**
     * Custom BeanFactoryPostProcessor to modify bean definitions *before* any beans are instantiated.
     */
    @Component
    public static class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // 0. Called *before* any bean instances are created.
            System.out.println("0. BeanFactoryPostProcessor: postProcessBeanFactory called.");
            if (beanFactory.containsBeanDefinition("myService")) {
                System.out.println("   BeanFactoryPostProcessor: Found 'myService' bean definition.");
                // In a real scenario, you could modify bean definitions here,
                // e.g., change scope, add properties, or replace a bean definition.
            }
        }
    }
}