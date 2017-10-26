package com.rice.server;

import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String... args) {
        getUsersFromJson("src/com/rice/server/Users.json");
//        do {
//        User dan = new User("dan", "dadler", true);
//        User zach = new User("zach", "ofpotatoes", false);
//        System.out.print(User.getUsernames());

//            if (User.getNumUsers() == 0) {
//                break;
//            }
//        } while (true);
    }

    private static void getUsersFromJson(String path) {
        Path file = FileSystems.getDefault().getPath(path);
        List<String> lines = new ArrayList<>();

        try{
                lines = Files.readAllLines(file);
        } catch (IOException e) {
            System.err.printf("Error: Cannot find ('%s').%n", path);
        }

//        JSONParser parser = new JSONParser();

        for (String line: lines) {
            System.out.printf("%s%n", line);
        }

    }
}
