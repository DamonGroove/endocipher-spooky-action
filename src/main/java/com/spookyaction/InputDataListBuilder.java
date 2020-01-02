package com.spookyaction;

import com.spookyaction.rest.SessionRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputDataListBuilder {
    public final int maxData = 128;
    public int requestIndex = 0;
    public List<long[]> inputDataList = new ArrayList<long[]>();

    // This method is used to add parameters to a list. A sublist list is created as a parameter for
    // a new instance of SessionRequest, if the size of the main list is divisible by the maxData.
    public void addToInputDataList(long duration, long dynamic, long positionX, long positionY) {
        inputDataList.add(new long[] {duration,dynamic, positionX, positionY});

        // Pass the value to a variable, since the size can change before the next reference
        int listSize = inputDataList.size();

        if(listSize % maxData == 0) {
            new SessionRequest(Main.login.sessionID, "", requestIndex, maxData,
                    inputDataList.subList(listSize - maxData, listSize - 1));
        }
    }

    public RequestEntity build() {
        // convert buffer to request entity
    }
}
