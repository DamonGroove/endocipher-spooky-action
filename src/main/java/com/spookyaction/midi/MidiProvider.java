package com.spookyaction.midi;

import com.spookyaction.Main;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.util.LinkedList;
import java.util.logging.Level;

public class MidiProvider {
    private Sequencer sequencer;
    private VirtualDevice virtualDevice;
    private MidiDevice midiDevice;

    public MidiProvider(MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }

    // Create a list of all available midi devices and check if a transmitter is available.
    public static LinkedList<MidiDevice.Info> getDeviceList() throws MidiUnavailableException {
        LinkedList<MidiDevice.Info> transmitterInfo = new LinkedList<MidiDevice.Info>();
        MidiDevice device;
        MidiDevice.Info[] devicesInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : devicesInfo) {
            device = MidiSystem.getMidiDevice(info);
            MidiSystem.getTransmitter();    // Documentation doesn't require this to be called but it is required

            if (device.getTransmitters().size() > 0) {
                transmitterInfo.add(info);
                Main.logger.log(Level.INFO, "Transmitter: " + info);
            }
        }
        return transmitterInfo;
    }

    // This is where the devices and sequencer are connected
    // Now the Midi data can be passed through to the Data List
    public void record() {
        this.openDevice(midiDevice);

        virtualDevice = new VirtualDevice();

        openSequencer();

        try {
            midiDevice.getTransmitter().setReceiver(virtualDevice);
            virtualDevice.setReceiver(sequencer.getReceiver());
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Close devices and sequencer
    public void stop() {
        sequencer.close();
        virtualDevice.close();
        midiDevice.close();
    }

    // Open the selected midi device
    private void openDevice(MidiDevice device) {
        Main.logger.log(Level.INFO, "Trying to open device. Is open: " + midiDevice.isOpen());
        device.close();

        if(!(midiDevice.isOpen())) {
            try {
                device.open();
                Main.logger.log(Level.INFO, "Device is Open");
            } catch (MidiUnavailableException e) {
                // Handle Midi Unavailable
                e.printStackTrace();
            }
        } else {
            Main.logger.log(Level.INFO, "Device open error");
        }
    }

    // Use the default sequencer for timing
    private void openSequencer() {
        try {
            sequencer = MidiSystem.getSequencer();
            if (sequencer == null) {
                Main.logger.log(Level.INFO," No Sequencer");
            } else {
                sequencer.open();
                Main.logger.log(Level.INFO, "Sequencer open");
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
