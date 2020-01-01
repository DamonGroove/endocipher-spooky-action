package com.spookyaction.midi.output;

import com.spookyaction.midi.input.OpenDevice;

import javax.sound.midi.*;

public class Record {

    OpenDevice openDevice;
    VirtualDevice virtualDevice;
    OpenSequencer openSequencer;

    MidiDevice midiDevice;
    public Record(MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }

    // This is where the devices and sequencer are connected
    // Now the Midi data can be passed through to the Data List
    public void record() {

        openDevice = new OpenDevice(midiDevice);
        openDevice.openDevice();

        virtualDevice = new VirtualDevice();

        openSequencer = new OpenSequencer();
        openSequencer.openSequencer();

        try {
            midiDevice.getTransmitter().setReceiver(virtualDevice);
            virtualDevice.setReceiver(openSequencer.sequencer.getReceiver());
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Close devices and sequencer
    public void stop() {
        openSequencer.closeSequencer();
        virtualDevice.close();
        openDevice.closeDevice();
    }
}
