package com.spookyaction.rest;

import com.spookyaction.Main;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;

public class LoginRequest {

    public Client client;
    public String sessionID;

    // Authenticate with the web service
    // Get a sessionID as a response
    public LoginRequest(String username, String password){

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        client = ClientBuilder.newClient( new ClientConfig()
                .register(feature)
                .register(JacksonFeature.class));

        String entity = client.target("http://localhost:8080/spooky/rest/v1")
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .header("Content-type","application/json")
                .get(String.class);

        Main.logger.log(Level.INFO, entity);

        sessionID = entity;
    }
}
