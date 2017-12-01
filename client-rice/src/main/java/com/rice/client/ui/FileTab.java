package com.rice.client.ui;

import com.rice.client.Client;
import com.rice.client.thread.TextWatcherThread;
import com.rice.client.util.GoogleDMP;
import com.rice.client.util.Print;
import com.rice.lib.Packet;
import com.rice.lib.packets.ModifyPacket;
import com.rice.lib.packets.ModifyType;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.LinkedList;

@SuppressWarnings("Duplicates")
public class FileTab extends Tab {

    private final CodeArea textArea;
    private boolean saved = false;
    private boolean sharing = false;
    private String filePath = null;
    private String fileName;
    private TextWatcherThread textWatcher;
    private String oldText = "";
    private String newText;

    public FileTab(String fileName) {
        this.fileName = fileName;
        this.textArea = new CodeArea();
        this.textWatcher = new TextWatcherThread(this);
        Print.debug("Starting TextWatcher...");
        this.textWatcher.start();
        this.setOnCloseRequest(e -> {
            Print.debug("Stopping TextWatcher...");
            this.textWatcher.cleanUp();
        });
        setUp();
    }

    private void setUp() {
        this.textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));

        // Text selection test
        textArea.setOnContextMenuRequested(e -> {
                    Print.debug("TEST: Selected text: \n" + textArea.getSelectedText() +
                            "\n Caret ParIndex: " + textArea.getCaretSelectionBind().getAnchorParIndex() +
                            "\n Caret Position: " + textArea.getCaretSelectionBind().getAnchorPosition() +
                            "\n Caret ColPosition: " + textArea.getCaretSelectionBind().getAnchorColPosition());
                    Client.packetQueue.add(new ModifyPacket(null, ModifyType.INSERT, -1, -1, textArea.getCaretSelectionBind().getAnchorPosition(), textArea.getCaretSelectionBind().getAnchorPosition()));
                }
        );



        //
        textArea.setOnKeyPressed(e -> {
            final int pos = textArea.caretPositionProperty().getValue();

            if (e.getCode() == KeyCode.BACK_SPACE) {
                System.out.println("back");
                Client.packetQueue.add(new ModifyPacket(null, ModifyType.DELETE, -1, -1, pos, pos));
                // Return cursor position
            } else if (e.getCode() == KeyCode.DELETE) {
                System.out.println("del");
                Client.packetQueue.add(new ModifyPacket(null, ModifyType.DELETE, -1, -1, pos, pos+1));
                // Return cursor position plus one
            }
        });

        final GoogleDMP googleDMP = new GoogleDMP();
        // Get the changes in strings
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("")) {
                this.oldText = oldValue;
                return;
            }
            final Boolean modified = Client.textAreaModified.poll();
            if(modified != null && modified == true) {
                return;
            }
            final LinkedList<GoogleDMP.Diff> tempList = googleDMP.diff_main(oldValue, newValue);
            String changed = "";
            for(final GoogleDMP.Diff d : tempList.toArray(new GoogleDMP.Diff[tempList.size()])) {
                System.out.println(String.format("Changed: text->%s op->%s old->%s", d.text, d.operation.name(), oldValue));
                if(d.operation != GoogleDMP.Operation.INSERT) {
                    continue;
                }
                changed = d.text;
                break;
            }
            if(changed.equals("")) {
                return;
            }
            final int pos = textArea.caretPositionProperty().getValue();
//            System.out.println(String.format("pos->%d", pos));
            Client.packetQueue.add(new ModifyPacket(changed, ModifyType.INSERT, -1, -1, pos, pos));
        });


        this.setText(fileName);
        this.setContent(new VirtualizedScrollPane<>(textArea));
    }

    public void clear() {
        this.textArea.clear();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setTextArea(String text) {
        clearTextArea();
        this.textArea.appendText(text);
    }

    public void appendTextArea(String text) {
        this.textArea.appendText(text);
    }

    public String getTextAreaContent() {
        return this.textArea.getText();
    }

    public void setTextAreaContent(String text) {
        this.textArea.clear();
        this.textArea.appendText(text);
    }

    private void clearTextArea() {
        this.textArea.clear();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.setText(fileName);
    }

    public String getFileExtension() {
        return FilenameUtils.getExtension(fileName);
    }

    public boolean hasFileExtension() {
        return getFileExtension() != null || !getFileExtension().equals("");
    }

    public boolean isSharing() {
        return sharing;
    }

    public void setSharing(boolean sharing) {
        this.sharing = sharing;
    }
}
