package com.spookyaction;

import com.spookyaction.midi.MidiProvider;
import com.spookyaction.rest.ApiClient;
import com.spookyaction.rest.entities.MidiSession;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main{

    public static ApiClient apiClient;
    public static Logger logger;
    public static MidiSession midiSession;

    public static void main(String[] args) {
        SpookyLogger spookyLogger = new SpookyLogger();
        spookyLogger.testLogger();

        LogManager logManager = LogManager.getLogManager();
        logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);

        apiClient = new ApiClient();
        apiClient.login("username", "password");

        try {

            // Need to clean up. Build a GUI.
            LinkedList<MidiDevice.Info> deviceList = MidiProvider.getDeviceList();
            System.out.println("Receive Midi");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a device: ");
            for (int i = 0; i < deviceList.size(); i++) {
                System.out.println(i + ": " + deviceList.get(i));
            }
            String selection = scanner.nextLine();
            System.out.println("You selected:" + selection);

            apiClient.createMidiCaptureSession();

            MidiProvider provider = new MidiProvider(MidiSystem.getMidiDevice(deviceList.get(Integer.parseInt(selection))));
            provider.record();

            System.out.println("Add to Duration Array");

            System.out.println("Continue to receive Midi");

            System.out.println("Enter q to quit: ");
            String terminateProgram = scanner.nextLine();// How did this work?

            if (terminateProgram.equals("q")) {
                provider.stop();
            }

        } catch (MidiUnavailableException e) {
            //
            e.printStackTrace();
        }
    }
}


