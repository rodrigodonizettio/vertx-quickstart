package main.br.com.oliveira.service;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executor;

@ApplicationScoped
public class GreetingService {
  //FIXME: See why the below happens
  /**
    * javax.enterprise.inject.spi.DeploymentException:
    * javax.enterprise.inject.UnsatisfiedResolutionException:
    * Unsatisfied deendency for type java.util.concurrent.Executor and qualifiers [@Default]
    **/
//  @Inject
//  Executor executor;

  /** If not set, address is the fully qualified name of the bean. e.g. "main.br.com.oliveira.service.GreetingService" **/
  @ConsumeEvent("greetingaddress1")
  public String consumeAndReply(String name) {
    return "GreetingService#consumeAndReply the received message (in Upper Case): " + name.toUpperCase();
  }

  @ConsumeEvent("greetingaddress2")
  public void consumeAndForget(Message<String> message) {
    System.out.println(message.address());
    System.out.println(message.body());
  }

//  @ConsumeEvent("greetingaddress1")
//  public Uni<String> processAndReply(String name) {
//    return Uni.createFrom().item(() -> name.toUpperCase()).emitOn(executor);
//  }

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
