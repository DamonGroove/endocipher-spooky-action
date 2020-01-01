package com.spookyaction.midi.device;

import com.spookyaction.Main;

import javax.sound.midi.*;
import java.util.Vector;
import java.util.logging.Level;

public class GetDevices {

    public Vector<MidiDevice.Info> getDeviceList() throws MidiUnavailableException {
        Vector<MidiDevice.Info> transmitterInfo = new Vector<MidiDevice.Info>();
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


}
