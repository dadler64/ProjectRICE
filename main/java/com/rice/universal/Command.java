package com.rice.universal;

import java.util.List;

public interface Command {
    // Example of how to use said command
    String getUsage();
    // Short description about said command
    String getHelp();
    // The actual execution of said command
    String doCommand(String[] input, int paramInt) throws CustomException;
}
