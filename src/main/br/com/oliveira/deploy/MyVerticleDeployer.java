package main.br.com.oliveira.deploy;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.Vertx;
import main.br.com.oliveira.verticle.MyBeanVerticle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;

@ApplicationScoped
public class MyVerticleDeployer {
  public void init(@Observes StartupEvent e, Vertx vertx, MyBeanVerticle verticle) {
    vertx.deployVerticle(verticle).await().indefinitely();
  }

  public void initEveryExposedAbstractVerticle(@Observes StartupEvent e, Vertx vertx, Instance<AbstractVerticle> verticles) {
    for(AbstractVerticle v : verticles) {
      vertx.deployVerticle(v).await().indefinitely();
    }
  }
}
