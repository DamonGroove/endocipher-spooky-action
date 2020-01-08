package com.spookyaction.midi;

import com.spookyaction.InputDataListBuilder;
import com.spookyaction.Main;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

public class VirtualDevice implements Transmitter, Receiver {

    private Receiver receiver;

    // Testing purposes Only extract timestamp once every 6 messages since the ManadalaDrum
    // sends out 6 messages per hit
    // Solve this by having the user test midi device during the device detection process
    // to determine how many midi messages are sent per trigger
    private int messagesPerEvent = 6;
    private int messageCount = 0;
    private int listSize = 0;
    private long sumDurations;
    private String startTimeStamp;

    InputDataListBuilder inputDataListBuilder = new InputDataListBuilder();

    @Override
    public void send(MidiMessage midiMessage, long timeStamp) {

        if(messageCount >= messagesPerEvent) {
            messageCount = 0;
        }

        if (messageCount == 0) {
            if(listSize == 0) {
                Date date = new Date();
                long time = date.getTime();
                startTimeStamp = new Timestamp(time).toString();
            }

            long duration = (timeStamp - sumDurations);
            long dynamic = 0;
            long positionX = 0;
            long positionY = 0;


            if (sumDurations == 0) {
                Main.logger.log(Level.SEVERE, "Duration is 0");
            } else {
                Main.logger.log(Level.INFO, "Output: " + duration + " : " + dynamic + " : " + positionX + " : " + positionY);

                // Add to the data list that will sent the the web service in pieces
                inputDataListBuilder.addToInputDataList(duration, dynamic, positionX, positionY);
                listSize++;

                if(listSize == Main.midiSession.getLimit()) {
                    // Some type of batching: Batch batch = inputDataListBuilder.build(Main.midiSession.getLimit(), startTimeStamp);
                    inputDataListBuilder.build(Main.midiSession.getLimit(), startTimeStamp);

                    // sent batch to API
                    // advanced version: Add batch to a queue that will be sent to the API by another thread
                    inputDataListBuilder = new InputDataListBuilder();
                    listSize = 0;
                }
            }
            sumDurations = timeStamp;
        }

        // Pass the original midi message and time stamp to the default receiver
        this.getReceiver().send(midiMessage, timeStamp);
        messageCount++;
    }

    @Override
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return this.receiver;
    }

    @Override
    public void close() {

    }
}
