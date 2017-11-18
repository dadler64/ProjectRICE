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
import com.rice.server.util.CommandLine;
import com.rice.server.util.CustomException;
import com.rice.server.util.Print;
import com.sun.media.jfxmedia.logging.Logger;
import javafx.application.Application;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class Server {

    private static final BufferedReader USER_INPUT = new BufferedReader(new InputStreamReader(System.in));
    private static final CommandLine COMMAND_LINE = new CommandLine();
    public static List<User> userList;
    public static boolean GUI = true;

    public static void main(String... args) {
        // Set up logger
        Logger.setLevel(Logger.ERROR);

        if (args.length > 0) {
            // Iterate through every argument
            for (int index = 0; index < args.length; index++) {
                // Terminal Mode
                if (args[index].equalsIgnoreCase("-t")) {
                    GUI = false;
                }
                // Show Help
                if (args[index].equalsIgnoreCase("-h")) {
                    Print.info("Usage: Server [-t]");
                    Print.info("-t : start in command-line mode");
                    return;
                }
            }
        }
        // Load users
        getUsersFromJson(Server.class.getClass().getResourceAsStream("/users.json"));
//        Print.debug("%d users loaded from ('%s')%n", userList.size(), USERS_FILE_PATH);
        // Determine whether to launch the GUI or terminal
        if (GUI) {
            // Launch GUI
            Application.launch(ServerGUI.class);
        } else {
            Print.info("Non-GUI mode activated!");
            while (true) {
                // Run the main server networking thread
            new Thread(new ServerCommunicationThread()).start();
                try {
//                    getInput();
                    String input;
                    while (true) {
                        Print.out(">> ");
                        if ((input = USER_INPUT.readLine()) != null) {

                            if (input.equalsIgnoreCase("exit")) {
                                System.exit(0);
                            }

                            COMMAND_LINE.scheduleCommand(input);

                            while (COMMAND_LINE.hasMoreCommands()) {
                                String command = COMMAND_LINE.getNextCommand();
                                Print.line(COMMAND_LINE.runCommand(command));
                            }
                        }
                    }
                } catch (IOException | CustomException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getUsersFromJson(InputStream inputStream) {
        final Reader reader = new InputStreamReader(inputStream);
        final Gson gson = new Gson();
        final Type user = new TypeToken<List<User>>() {
        }.getType();
        userList = gson.fromJson(reader, user);
    }

    public static List<User> getUserList() {
        return userList;
    }
}
