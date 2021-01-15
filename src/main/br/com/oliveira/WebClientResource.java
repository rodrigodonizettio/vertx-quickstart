package main.br.com.oliveira;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/star-wars-data")
public class WebClientResource {

    @Inject
    Vertx vertx;

    private WebClient webClient;

    @PostConstruct
    protected void initialize() {
        this.webClient = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("swapi.dev")
                        .setDefaultPort(443).setSsl(true).setTrustAll(true));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/character/{code}")
    public Uni<JsonObject> getCharacterData(@PathParam("code") String code) {
        return webClient.get("/api/people/" + code)
                .send()
                .onItem().transform(res -> {
                    if(res.statusCode() == 200) {
                        return res.bodyAsJsonObject();
                    } else {
                        return new JsonObject()
                                .put("code", res.statusCode())
                                .put("messsage", res.bodyAsString());
                    }
                });
    }
}