package main.br.com.oliveira;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.time.Duration;

@Path("/lorem")
public class LoremResource {

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> readFileUsingAsyncVertxMutinyUniBuffer() {
        return vertx.fileSystem().readFile(getSampleFilePath())
                .onItem().transform(buffer -> buffer.toString("UTF-8"));
    }

    @Path("/fake-async")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> doAFakeAsyncComputation() {
        return Uni.createFrom()
                .item(() -> "Lorem!")
                .onItem().delayIt().by(Duration.ofMillis(5000));
    }

    private String getSampleFilePath() {
        return new File("main/resources/sample-lorem.txt").getPath();
    }
}