package com.spookyaction.rest;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ApiClient {
    private String sessionToken;
    private int captureSessionID;
    private Client client;

    public ApiClient() {
        client = ClientBuilder.newClient();
    }

    public void login(String username, String password) {
        // login to backend and set sessionToken
    }

    public void createMidiCaptureSession() {
        // Create new capture session and set catpureSessionID
    }

    public void addMidiDataToSession(MidiData data) {

    }

    public void completeCaptureSession() {

    }
}
