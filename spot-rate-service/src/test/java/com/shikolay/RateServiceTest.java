package com.shikolay;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.shikolay.dao.JSONRanges;
import com.shikolay.dto.ResultedRate;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RateServiceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        JSONRanges jsonDAO = JSONRanges.getInstance();
        jsonDAO.loadDataFromFile("mock.json");
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testGetRate() {
        ResultedRate responseMsg = target.path("rate")
                .queryParam("from", "2015-07-01T07:00:00Z")
                .queryParam("to", "2015-07-01T12:00:00Z")
                .request().get(ResultedRate.class);
        assertTrue("1500".equals(responseMsg.getRate()));
    }
}
