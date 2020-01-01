package com.spookyaction.rest;


import com.spookyaction.Main;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;

public class SessionRequest {

    private JSONArray jsonData = new JSONArray();

    private String sessionID;
    private String startTimeStamp;
    private int requestIndex;
    private int maxData;
    private List<long[]> smallDataList;

    public SessionRequest(String sessionID, String startTimeStamp, int requestIndex, int maxData,
                          List<long[]> smallDataList) {
        this.sessionID = sessionID;
        this.startTimeStamp = startTimeStamp;
        this.requestIndex = requestIndex;
        this.maxData = maxData;
        this.smallDataList = smallDataList;

        postData();
    }

    // Send POST request using the same login client to authenticate
    // Include the sessionID and the sub list smallDataList
    private void postData() {
        buildJsonData();

        String response = Main.login.client.target("http://localhost:8080/spooky/rest/v1")
                .path("session")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(buildJson(), MediaType.APPLICATION_JSON))
                .readEntity(String.class);

        Main.logger.log(Level.INFO, response);
    }

    // Insert the small data list into the jsonData array
    private void buildJsonData() {
        for (long[] item: smallDataList
             ) {
            insertData(item[0], item[1], item[2], item[3]);
        }
    }

    // Insert data into a map that will be put into a JSONArray passed to POST JSON
    private void insertData(long duration, long dynamic, long positionX, long positionY) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("duration", Long.toString(duration));
        map.put("dynamic", Long.toString(dynamic));
        map.put("positionX", Long.toString(positionX));
        map.put("positionY", Long.toString(positionY));
        jsonData.put(map);
    }

    // Build the JSON that will be posted
    public String buildJson() {
        Date date = new Date();
        long time = date.getTime();

        return new JSONObject()
                .put("userID", requestIndex)
                .put("sessionID", sessionID)
                .put("timeStamp", new Timestamp(time))
                .put("data", jsonData).toString();
    }
}
