package main.br.com.oliveira.service;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executor;

@ApplicationScoped
public class GreetingService {
  @Inject
  Executor executor;

  /** If not set, address is the fully qualified name of the bean. e.g. "main.br.com.oliveira.service.GreetingService" **/
  @ConsumeEvent("greeting-address1")
  public String consume(String name) {
    return name.toUpperCase();
  }

  @ConsumeEvent("greeting-address1")
  public Uni<String> process(String name) {
    return Uni.createFrom().item(() -> name.toUpperCase()).emitOn(executor);
  }

  /** For blocking consumer code use the parameter "blocking = true" inside @ConsumeEvent or use the annotation @Blocking **/
//  @ConsumeEvent(value = "blocking-consumer", blocking = true)
  @ConsumeEvent(value = "blocking-consumer")
  @Blocking
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
