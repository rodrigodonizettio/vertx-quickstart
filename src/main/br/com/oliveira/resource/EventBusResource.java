package main.br.com.oliveira.resource;

import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import main.br.com.oliveira.codec.MyNameCodec;
import main.br.com.oliveira.model.MyName;
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
  @Path("/send-reply/{name}")
  public Uni<String> sendMessageAndExpectReply(@PathParam String name) {
    return eventBus.<String>request("greetingaddress1", name)
            .onItem().transform(Message::body);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/general-codec/{name}")
  public Uni<String> sendSerializedDeserializedMessageAndExpectReply(@PathParam String name) {
    return eventBus.<String>request("greetingaddress5", new MyName(name))
            .onItem().transform(Message::body);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/specific-codec/{name}")
  public Uni<String> sendSerializedDeserializedMessageUsingSpecificCodecAndExpectReply(@PathParam String name) {
    return eventBus.<String>request("greetingaddress6", name,
            new DeliveryOptions().setCodecName(MyNameCodec.class.getName()))
            .onItem().transform(Message::body);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/single-consumer/{name}")
  public EventBus sendMessageToSpecificAddressWhereSingleConsumerReceivesTheMessage(@PathParam String name) {
    return eventBus.sendAndForget("greetingaddress2", name);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/publish/{name}")
  public EventBus publishMessageToSpecificAddressWhereAllConsumersReceiveTheMessages(@PathParam String name) {
    return eventBus.sendAndForget("greetingaddress3", name);
  }
}
