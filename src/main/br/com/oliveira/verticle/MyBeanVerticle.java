package main.br.com.oliveira.verticle;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyBeanVerticle extends AbstractVerticle {
  @ConfigProperty(name = "address")
  String address;

  @Override
  public Uni<Void> asyncStart() {
    return vertx.eventBus().consumer(address)
            .handler(m -> m.replyAndForget("MyBean Verticle Greetings from Address-2"))
            .completionHandler();
  }

  private void deployVerticle() {
    vertx.deployVerticle(this.getClass().getName());
//    deployVerticle(MyBeanVerticle.class.getName(), ar -> { }); //Deploying verticle with options
    vertx.deployVerticle(new MyBeanVerticle());
//    deployVerticle(new MyBeanVerticle(), ar -> { }); //Deploying verticle with options
  }
}
