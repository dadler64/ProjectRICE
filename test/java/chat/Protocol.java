package chat;


import static chat.Status.*;

public class Protocol {

//    private int currentState = WAITING;
    private Status state = WAITING;

    private String[] prompts = {"hi", "testing my chat program", "great. then i will submit my lab"};
    private String[] responses = { "What's up?", "Your chat program works well!", "Ok. Have fun!"};

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Hello! this is a chat server";
            state = SENTMSG0;
        }
        else if (state == SENTMSG0) {
            if (theInput.equalsIgnoreCase(prompts[0])) {
                theOutput = responses[0];
                state = SENTMSG1;
            } else {
                theOutput = String.format("You're supposed to say \"%s\"%n.", prompts[0]);
            }
        } else if (state == SENTMSG1) {
            if (theInput.equalsIgnoreCase(prompts[1])) {
                theOutput = responses[1];
                state = END;
            } else {
                theOutput = String.format("You're supposed to say \"%s\"%n.", prompts[1]);
            }
        }
        else if (state == END) {
            if (theInput.equalsIgnoreCase(prompts[2])) {
                theOutput = responses[2];
            } else {
                theOutput = String.format("You're supposed to say \"%s\"%n", prompts[2]);
            }
        }
        return theOutput;
    }
}