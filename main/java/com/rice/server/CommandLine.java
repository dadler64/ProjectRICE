package com.rice.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class CommandLine {

    private Map<String, Command> commands;
    private LinkedList<String> commandQueue;

    public CommandLine() {
        setupCommands();
        commands = new HashMap<>();
        commandQueue = new LinkedList<>();
    }

    String getNextCommand() {
        return this.commandQueue.removeFirst();
    }

    boolean hasMoreCommands() {
        return this.commandQueue.size() != 0;
    }

    void scheduleCommand(String command) {
        if (command.equalsIgnoreCase("stop")) {
            this.commandQueue.addFirst(command);
        } else {
            this.commandQueue.add(command);
        }
    }

    private void setupCommands() {
        // Command to display general help or the usage of a specific command
        this.commands.put("help",new Command() {
            public String getUsage () {
                return "h[elp] [command]";
            }

            public String getHelp () {
                return "Print out help for all available commands, or for just a specified command.";
            }

            public String doCommand (String[]paramAnonymousArrayOfString,int paramAnonymousInt){
//                if (paramAnonymousInt > 2) {
//                    return getUsage();
//                }
//                if (paramAnonymousInt == 1) {
//                    String localObject = "";
//                    Iterator<Command> localIterator = CommandLine.this.commandsSet.iterator();
//
//                    while (localIterator.hasNext()) {
//                        Command localCommand = localIterator.next();
//                        String str1 = localCommand.getUsage();
//                        String str2 = str1.split("\\s+")[0];
//                        localObject = (String) localObject + str2 + " usage: " + str1 + "\n";
//                    }
//                    return (String) localObject;
//                }
//                Command localObject = CommandLine.this.commands.get(paramAnonymousArrayOfString[1].toLowerCase());
//
//                if (localObject == null) {
//                    return paramAnonymousArrayOfString[1] + ": command not found";
//                }
//                return "usage: " + (localObject).getUsage() + "\n   " + (localObject).getHelp();
                return "Help Command WIP";
            }
        });
        // Add command 'w'
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
                return User.getUsernames();
            }
        });
        this.commands.put("w", this.commands.get("whoishere"));
    }

    String runCommand(String command) {
        return command;
    }
}
