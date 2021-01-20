package main.br.com.oliveira.service;

import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {
  /** If not set, address is the fully qualified name of the bean. e.g. "main.br.com.oliveira.service.GreetingService" **/
  @ConsumeEvent
  public String consume(String name) {
    return name.toUpperCase();
  }

  /** For blocking consumer code use the parameter "blocking = true" **/
  @ConsumeEvent(value = "blocking-consumer", blocking = true)
  public void consumeBlocking(String message) {
    doSomethingBlocking();
  }

  private void doSomethingBlocking() {
    for(int i=0; i<1000; i++) {
      try {
        System.out.println("GreetingService#doSomethingBlocking is under execution");
        System.in.read();
      } catch(Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
