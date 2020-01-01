package com.spookyaction;

import com.spookyaction.midi.device.GetDevices;
import com.spookyaction.midi.output.Record;
import com.spookyaction.rest.LoginRequest;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main{

    // Needs to be more secure
    public static LoginRequest login;

    public static Logger logger;

    public static void main(String[] args) {
        SpookyLogger spookyLogger = new SpookyLogger();
        spookyLogger.testLogger();

        LogManager logManager = LogManager.getLogManager();
        logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);


        GetDevices getDevices = new GetDevices();

        login = new LoginRequest("username", "password");

        try {

            // Need to clean up. Build a GUI.
            Vector<MidiDevice.Info> deviceList = getDevices.getDeviceList();
            System.out.println("Receive Midi");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a device: ");
            for (int i = 0; i < deviceList.size(); i++) {
                System.out.println(i + ": " + deviceList.get(i));
            }
            String selection = scanner.nextLine();
            System.out.println("You selected:" + selection);

            Record record = new Record(MidiSystem.getMidiDevice(deviceList.get(Integer.parseInt(selection))));
            record.record();

            System.out.println("Add to Duration Array");

            System.out.println("Continue to receive Midi");

            System.out.println("Enter q to quit: ");
            String terminateProgram = scanner.nextLine();// How did this work?

            if (terminateProgram.equals("q")) {
                record.stop();
            }

        } catch (MidiUnavailableException e) {
            //
            e.printStackTrace();
        }
    }


}


