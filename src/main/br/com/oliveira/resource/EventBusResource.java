package main.br.com.oliveira.resource;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/async")
public class EventBusResource {
  @Inject
  EventBus eventBus;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("{name}")
  public Uni<String> sendMessageAndExpectReply(@PathParam String name) {
    return eventBus.<String>request("greeting-address1", name)
            .onItem().transform(Message::body);
  }

  @Path("{name}")
  public EventBus sendMessageToSpecificAddressWhereSingleConsumerReceivesTheMessage(@PathParam String name) {
    return eventBus.sendAndForget("greeting-address1", name);
  }

  @Path("{name}")
  public EventBus publishMessageToSpecificAddressWhereAllConsumersReceiveTheMessages(@PathParam String name) {
    return eventBus.sendAndForget("greeting-address1", name);
  }
}
