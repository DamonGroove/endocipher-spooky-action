package com.spookyaction.midi.output;

import com.spookyaction.Main;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.util.logging.Level;

public class OpenSequencer {

    public Sequencer sequencer;

    public OpenSequencer(){

    }
    // Use the default sequencer for timing
    public void openSequencer() {

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

    public void closeSequencer() {
        sequencer.close();
    }
}
