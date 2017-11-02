package com.rice.universal;

import com.rice.server.Server;
import com.rice.server.ui.ServerGUI;

import javafx.application.Application;

import java.util.*;

import static com.rice.universal.Utils.getSpaces;

public class CommandLine {

    private Map<String, Command> commands = new HashMap<>();
    private LinkedList<String> commandQueue = new LinkedList<>();
    private TreeSet<Command> commandTreeSet = new TreeSet<>();
    private Stack<String> prevHistoryStack = new Stack<>();
    private Stack<String> nextHistoryStack = new Stack<>();

    public CommandLine() {
        setupCommands();
    }

    public String getNextCommand() {
        return this.commandQueue.removeFirst();
    }

    public boolean hasMoreCommands() {
        return this.commandQueue.size() != 0;
    }

    public void scheduleCommand(String command) {
        if (command.equalsIgnoreCase("stop")) {
            this.commandQueue.addFirst(command);
        } else {
            this.commandQueue.add(command);
        }
    }

    public String getPreviousHistory() {
        if (this.prevHistoryStack.isEmpty()) {
            return "";
        }
        String str = this.prevHistoryStack.pop();
        this.nextHistoryStack.push(str);
        return str;
    }

    public String getNextHistory() {
        if (this.nextHistoryStack.isEmpty()) {
            return "";
        }
        String str = this.nextHistoryStack.pop();
        this.prevHistoryStack.push(str);
        return str;
    }

    public void addToHistory(String input) {
        if (this.prevHistoryStack.empty()) {
            this.prevHistoryStack.push(input);
        } else if (!this.prevHistoryStack.peek().equalsIgnoreCase(input)) {
            this.prevHistoryStack.push(input);
        }
    }

    public void resetHistoryStack() {
        while (!this.nextHistoryStack.empty()) {
            this.prevHistoryStack.push(this.nextHistoryStack.pop());
        }
    }

    private void setupCommands() {
        // Simple test command
        this.commands.put("test", new Command() {
            public String getUsage () {
                return "test";
            }

            public String getHelp () {
                return "This is a simple test command.";
            }

            public String doCommand (String[] inputArr ,int paramAnonymousInt){
                return "Congratulations your test command works!";
            }
        });
        this.commands.put("tst", this.commands.get("test"));

        // Clear the console

        this.commands.put("start", new Command() {
            @Override
            public String getUsage() {
                return "st[art]";
            }

            @Override
            public String getHelp() {
                return "Starts a new thread which starts the networking portion of the server.";
            }

            @Override
            public String doCommand(String[] input, int paramInt) throws CustomException {
//                new Thread(new GUIServerThread()).start();
                return "Server thread started...\n";
            }
        });

        // Run server GUI TODO: Add the ability to pick the class
        this.commands.put("launch", new Command() {
            @Override
            public String getUsage() {
                return "launch";
            }

            @Override
            public String getHelp() {
                return "Launches the Server's GUI if it is not already running.";
            }

            @Override
            public String doCommand(String[] input, int paramInt) throws CustomException {
                if (Server.GUI) {
                    return "Warning: GUI may already be running!\n";
                } else if (!Server.GUI) {
                    Server.GUI = true;
                    Application.launch(ServerGUI.class);
                    Server.GUI = false;
                    return "Closed out of GUI.\n";
                } else {
                    return "Warning: GUI is in an unknown state!\n";
                }
            }
        });

        // Add 'help' command
        this.commands.put("help", new Command() {
            public String getUsage () {
                return "h[elp] [command]";
            }

            public String getHelp () {
                return "Print out help for a specified command.";
            }

            public String doCommand (String[] inputArr ,int paramAnonymousInt){
                String command;
                try {
                    if (inputArr.length == 1) {
                        // Print help for all commands in the
                        StringBuilder sb = new StringBuilder();
                        sb.append("These are the following commands available:\n");
                        for(String c: commands.keySet()) {
                            if (c.length() > 1) {
                                sb.append(String.format("Usage: %s - %s%n", commands.get(c).getUsage(), commands.get(c).getHelp()));
                            }
                        }
//                        sb.append("Type: 'h[elp] [command]' to learn more about a specific command.\n");
                        return sb.toString();
                    } else if (inputArr.length == 2) {
                        command = inputArr[1];
                        if (commands.containsKey(command)) {
                            return String.format("Usage: %s%n%s", commands.get(command).getUsage(), commands.get(command).getHelp());
                        }
                    } else {
                        return String.format("Unknown command: ('%s')", Arrays.toString(inputArr));
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Error: Incorrect number of arguments!");
                }
                return "Error: Incorrect argument length.";
            }
        });
        this.commands.put("h", this.commands.get("help"));

        // Add 'w' command
        this.commands.put("whoishere", new Command() {

            @Override
            public String getUsage() {
                return "w[hoishere]";
            }

            @Override
            public String getHelp() {
                return "List the users that are currently logged in. ";
            }

            @Override
            public String doCommand(String[] input, int paramInt) throws CustomException{

                StringBuilder sb = new StringBuilder();
                sb.append("       Users:       |     Passwords:     | LoginRecord Status:\n");
                sb.append("--------------------|--------------------|---------------\n");
                for (User user: Server.userList) {
                    sb.append(String.format("  %s%s |  %s%s  |     %b%n", user.getUsername(), getSpaces(user.getUsername()), user.getPassword(), getSpaces(user.getPassword()), user.getStatus().toString()));
                }
                sb.append("\n");
                return sb.toString();
            }
        });
        this.commands.put("w", this.commands.get("whoishere"));
    }

    public String runCommand(String input) throws CustomException {
        if (input == null) {
            return "";
        }
        String[] inputArr = input.split("\\s+");
        int i = inputArr.length;
        if (i == 0) {
            return "";
        }

        String str = inputArr[0].toLowerCase();

        if (str.equals("")) {
            return "";
        }

        Command command = this.commands.get(str);
        if (command == null) {
            return "Unknown command: " + str;
        }

        return command.doCommand(inputArr, i);
    }
}
