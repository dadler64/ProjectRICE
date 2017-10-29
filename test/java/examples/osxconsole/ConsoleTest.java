package examples.osxconsole;

import javax.swing.*;

public class ConsoleTest {
    public static void main(String[] args) {
        JFrame f = new JFrame(); //creating instance of JFrame

        OSXConsole console = new OSXConsole();

        f.add(console); //adding button in JFrame

        f.setSize(400,500); //400 width and 500 height
        f.setLayout(null); //using no layout managers
        f.setVisible(true); //making the frame visible
    }
}