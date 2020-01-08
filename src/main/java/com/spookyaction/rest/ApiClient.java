package com.spookyaction.rest;

import com.spookyaction.Main;
import com.spookyaction.rest.entities.MidiData;
import com.spookyaction.rest.entities.MidiSession;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

public class ApiClient {
    private String sessionToken;
    private Long midiSessionID;
    private Client client;
    private Integer requestIndex = 0;
    private Integer limit;

    public ApiClient() {
        client = ClientBuilder.newClient();
    }

    public void login(String username, String password) {
        // login to backend and set sessionToken
        sessionToken = client.target("http://localhost:8080/spooky/rest/v1")
                .path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(String.class, MediaType.APPLICATION_JSON))
                .readEntity(String.class);
    }

    public void createMidiCaptureSession() {
        // Create new capture session and set catpureSessionID
        MidiSession entity = client.target("http://localhost:8080/spooky/rest/v1")
                .path("session")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization",sessionToken)
                .get(MidiSession.class);

        midiSessionID = entity.getMidiSessionID();
        limit = entity.getLimit();
    }

    public void addMidiDataToSession(MidiData data) {
        data.setId(requestIndex);
        data.setMidiSessionID(midiSessionID);
        data.setLimit(limit);

        Response response = client.target("http://localhost:8080/spooky/rest/v1")
                .path("session")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization",sessionToken)
                .post(Entity.entity(data, MediaType.APPLICATION_JSON));

        Main.logger.log(Level.INFO, String.valueOf(response.getStatus()));
        Main.logger.log(Level.INFO, response.readEntity(MidiData.class).toString());

        requestIndex++;
    }

    public void completeCaptureSession() {
        // Complete session
    }
}
