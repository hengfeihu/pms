package com.hfh;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.hfh.config.DataSourceManager;
import com.hfh.filter.AirLogFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class App extends ResourceConfig {

    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://172.16.1.105").port(9998).build();
        ResourceConfig config = new ResourceConfig();
        config.packages("com.hfh.resource");
        config.register(LoggingFeature.class);
        config.register(JacksonJsonProvider.class);
        config.register(AirLogFilter.class);
        DataSourceManager dataSourceManager = new DataSourceManager();
        dataSourceManager.init();
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
