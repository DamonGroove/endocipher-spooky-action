package com.spookyaction.rest.entities;

import java.util.List;

public class MidiData {


    private Integer id;
    private Long midiSessionID;
    private String startTimeStamp;
    private Integer limit;
    private List<long[]> smallDataList;

    public MidiData() {}

    public MidiData(String startTimeStamp, List<long[]> smallDataList) {
        this.startTimeStamp = startTimeStamp;
        this.smallDataList = smallDataList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMidiSessionID() {
        return midiSessionID;
    }

    public void setMidiSessionID(Long midiSessionID) {
        this.midiSessionID = midiSessionID;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<long[]> getSmallDataList() {
        return smallDataList;
    }

    public void setSmallDataList(List<long[]> smallDataList) {
        this.smallDataList = smallDataList;
    }
}
