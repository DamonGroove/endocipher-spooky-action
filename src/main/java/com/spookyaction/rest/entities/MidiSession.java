package com.spookyaction.rest.entities;

public class MidiSession {
    private Long id;
    private Long midiSessionID;
    private Integer limit;

    public MidiSession(){}

    public Long getId() {
        return id;
    }

    public Long getMidiSessionID() {
        return midiSessionID;
    }

    public Integer getLimit() {
        return limit;
    }
}
