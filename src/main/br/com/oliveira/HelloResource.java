package main.br.com.oliveira;

import io.vertx.mutiny.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Path("/hello")
public class HelloResource {

    @Inject
    Vertx vertx;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        readFileUsingAsyncVertxMutinyUniBuffer();
        return "Hello RESTEasy";
    }

    private String getSampleFilePath() {
        return new File("../src/main/resources/sample-name.txt").getPath();
    }

    private void readFileUsingAsyncVertxMutinyUniBuffer() {
        vertx.fileSystem().readFile(getSampleFilePath())
                .onItem().transform(buffer -> buffer.toString("UTF-8"))
                .subscribe()
                .with(
                        content -> System.out.println("Content: " + content),
                        err -> System.out.println("Cannot read the file: " + err.getMessage())
                );
    }
}