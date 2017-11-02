/*
 * Copyright [2017] [Dan Adler <adlerd@wit.edu>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rice.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.rice.server.ui.ServerGUI;
import com.rice.universal.CommandLine;
import com.rice.universal.CustomException;
import com.rice.universal.SplitOutputStream;
import com.rice.universal.User;

import javafx.application.Application;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final BufferedReader USER_INPUT = new BufferedReader(new InputStreamReader(System.in));
    private static final CommandLine COMMAND_LINE = new CommandLine();
    private static final String USERS_FILE_PATH = "main/resources/Users.json";
    public static List<User> userList;
    public static boolean GUI = true;

    public static void main(String... args) {
        // Set up split output stream
        try {
            final File logFile = new File("out.log");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            final FileOutputStream fileOutputStream = new FileOutputStream(logFile);
            final SplitOutputStream splitOutputStream = new SplitOutputStream(System.out, fileOutputStream);
            final PrintStream printStream = new PrintStream(splitOutputStream);
            System.setOut(printStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str1 = "";
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("-t")) {
                GUI = false;
                if (args.length > 2 && args[1].equalsIgnoreCase("-s")) {
                    str1 = args[2];
                }
            }
            if (args[0].equalsIgnoreCase("-h")) {
                System.out.println("Usage: Server [-t]");
                System.out.println("-t : start in command-line mode");
                return;
            }
        }
        // Load users
        getUsersFromJson(USERS_FILE_PATH);
        System.out.printf("%d users loaded from ('%s')%n", userList.size(), USERS_FILE_PATH);
        // Determine whether to launch the GUI or terminal
        if (GUI) {
            // Launch GUI
            Application.launch(ServerGUI.class);
        } else {
            System.out.println("Non-GUI mode activated!");
            while (true) {
                // Run the main server networking thread
            new Thread(new ServerCommunicationThread()).start();
                try {
//                    getInput();
                    String input;
                    while (true) {
                        System.out.print(">> ");
                        if ((input = USER_INPUT.readLine()) != null) {

                            if (input.equalsIgnoreCase("exit")) {
                                System.exit(2);
                            }

                            COMMAND_LINE.scheduleCommand(input);

                            while (COMMAND_LINE.hasMoreCommands()) {
                                String command = COMMAND_LINE.getNextCommand();
                                System.out.printf("%s%n", COMMAND_LINE.runCommand(command));
                            }
                        }
                    }
                } catch (IOException | CustomException e) {
                    e.printStackTrace();
                }
            }
        }
    }



//    public static void getInput() throws IOException, CustomException {
//        String input;
//        while (true) {
//            System.out.print(">> ");
//            if ((input = USER_INPUT.readLine()) != null) {
//
//                if (input.equalsIgnoreCase("exit")) {
//                    System.exit(2);
//                }
//
//                COMMAND_LINE.scheduleCommand(input);
//
//                while (COMMAND_LINE.hasMoreCommands()) {
//                    String command = COMMAND_LINE.getNextCommand();
//                    System.out.printf("%s%n", COMMAND_LINE.runCommand(command));
//                }
//            }
//        }
//    }

    public static List<User> getUserList() {
        return userList;
    }

    private static void getUsersFromJson(String path) {
        final Path file = FileSystems.getDefault().getPath(path);
        final Gson gson = new Gson();
        final Type type = new TypeToken<List<User>>(){}.getType(); // TODO: Figure out why this is throwing a NullPointerException

        // Read JSON file to an ArrayList
        try{
            final List<String> users = Files.readAllLines(file);
//            List<User> fromJson = gson.fromJson(users.get(0), type);
            userList = gson.fromJson(users.get(0), type);

            // Add users to the static list in server
//            for (User u: fromJson) {
//                userList.add(u);
//            }
        } catch (IOException e) {
            System.err.printf("Error: Cannot find ('%s').%n", path);
        }
    }
}
