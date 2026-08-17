# Spring BeanPostProcessor BeanFactoryPostProcessor Demo

This example demonstrates Spring's `BeanPostProcessor` and `BeanFactoryPostProcessor` interfaces. `BeanFactoryPostProcessor` modifies bean definitions before any beans are instantiated, allowing for configuration changes at an early stage. `BeanPostProcessor` allows custom logic to be applied to bean instances both before and after their initialization callbacks, such as `@PostConstruct`. The console output clearly illustrates the execution order of these processors within the Spring bean lifecycle.

## Language

`java`

## How to Run

1. Ensure you have JBang installed (e.g., `curl -Ls https://sh.jbang.dev | bash -s -- install`).
2. Save the code as `BeanProcessorsDemoApplication.java`.
3. Run the application using `jbang BeanProcessorsDemoApplication.java`.

## Original Article

This example accompanies the Turkish article: [Spring Uygulamalarınızda Daha Fazla Kontrol: BeanPostProcessor ve BeanFactoryPostProcessor'ı Anlamak](https://fatihsoysal.com/blog/spring-uygulamalarinizda-daha-fazla-kontrol-beanpostprocessor-ve-beanfactorypostprocessori-anlamak/).

## License

MIT — see [LICENSE](LICENSE).
