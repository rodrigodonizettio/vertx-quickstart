package main.br.com.oliveira;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hi")
@Produces(MediaType.APPLICATION_JSON)
public class VertxJsonHiResource {

    @GET
    @Path("{name}/object")
    public JsonObject hiJsonObject(@PathParam String name) {
        return new JsonObject().put("Hi", name);
    }

    @GET
    @Path("{name}/array")
    public JsonArray hiJsonArray(@PathParam String name) {
        return new JsonArray().add("Hi").add(name);
    }
}