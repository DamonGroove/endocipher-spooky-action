package com.spookyaction.midi.in;

import com.spookyaction.Main;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import java.util.logging.Level;

public class OpenDevice {

    MidiDevice device;

    public OpenDevice(MidiDevice device) {
        this.device = device;
    }

    public void openDevice() {
        Main.logger.log(Level.INFO, "Trying to open device. Is open: " + this.device.isOpen());
        this.device.close();

        if(!(this.device.isOpen())) {
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

    public void closeDevice() {
        device.close();
    }
}
