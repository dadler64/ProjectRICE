package examples.osxconsole;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;

public class OSXConsole extends JPanel {
    public static final long serialVersionUID = 21362469L;

    private JTextPane textPane;
    private PipedOutputStream pipeOut;
    private PipedInputStream pipeIn;


    public OSXConsole() {
        super(new BorderLayout());
        textPane = new JTextPane();
        this.add(textPane, BorderLayout.CENTER);

        redirectSystemStreams();

        textPane.setBackground(Color.GRAY);
        textPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    }


    private void updateTextPane(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Document doc = textPane.getDocument();
                try {
                    doc.insertString(doc.getLength(), text, null);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
                textPane.setCaretPosition(doc.getLength() - 1);
            }
        });
    }


    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(final int b) throws IOException {
                updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }


}