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
import com.rice.universal.CommandLine;
import com.rice.universal.CustomException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.List;

public class Server {

    public static void main(String... args) throws IOException, CustomException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        getUsersFromJson("main/resources/Users.json");
        String input;
        CommandLine commandLine = new CommandLine();

        while (true) {
            System.out.print(">> ");
            if ((input = userInput.readLine()) != null) {

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                commandLine.scheduleCommand(input);

                while (commandLine.hasMoreCommands()) {
                    String command = commandLine.getNextCommand();
                    System.out.printf("%s%n", commandLine.runCommand(command));
                }
            }
        }
    }

    private static void readInput(int read) {

    }

    private static void getUsersFromJson(String path) {
        Path file = FileSystems.getDefault().getPath(path);
        List<String> users;
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();

        // Read JSON file to an ArrayList
        try{
            users = Files.readAllLines(file);
            List<User> fromJson = gson.fromJson(users.get(0), type);

            // Add users to the static list users in User
            for (User u: fromJson) {
                User.addUser(u);
            }
        } catch (IOException e) {
            System.err.printf("Error: Cannot find ('%s').%n", path);
        }
    }
}
