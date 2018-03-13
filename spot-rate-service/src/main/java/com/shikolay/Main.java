package com.shikolay;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.shikolay.dao.JSONRanges;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        //Prepare metrics registry
        String registryName = "default";
        SharedMetricRegistries.getOrCreate(registryName);
        SharedMetricRegistries.setDefault(registryName);

        // create a resource config that scans for JAX-RS resources and providers
        // in com.shikolay package
        final ResourceConfig rc = new ResourceConfig()
                .register(new InstrumentedResourceMethodApplicationListener(SharedMetricRegistries.getDefault()))
                .packages("com.shikolay")
                .register(JacksonFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String dataPath = "data.json";
        if (args.length > 0) {
            dataPath = args[0];
        }

        JSONRanges dataSource = JSONRanges.getInstance();
        dataSource.loadDataFromFile(dataPath);

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        System.in.read();
        server.shutdownNow();
    }
}

