package com.hfh;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class App extends ResourceConfig {

    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig();
        config.packages("com.hfh.resources");
        config.register(LoggingFeature.class);
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            server.start();
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
