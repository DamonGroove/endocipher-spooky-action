package com.spookyaction;

import com.spookyaction.rest.entities.MidiData;

import java.util.ArrayList;
import java.util.List;

public class InputDataListBuilder {
    public List<long[]> inputDataList = new ArrayList<long[]>();

    public void addToInputDataList(long duration, long dynamic, long positionX, long positionY) {
        inputDataList.add(new long[] {duration,dynamic, positionX, positionY});
    }

    public void build(Integer limit, String startTimeStamp) {
        // convert buffer to request entity
        Main.apiClient.addMidiDataToSession(new MidiData(startTimeStamp, inputDataList));
    }
}
