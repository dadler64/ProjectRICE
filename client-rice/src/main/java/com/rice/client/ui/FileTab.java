package com.rice.client.ui;

import com.rice.client.thread.TextWatcherThread;
import com.rice.client.util.Print;
import javafx.scene.control.Tab;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

@SuppressWarnings("Duplicates")
public class FileTab extends Tab {

    private final CodeArea textArea;
    private boolean saved = false;
    private boolean sharing = false;
    private String filePath = null;
    private String fileName;
    private TextWatcherThread textWatcher;
    private String oldText;
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

    public void undo() {
        this.textWatcher.undo();
    }

    public void redo() {
        this.textWatcher.redo();
    }

    private void setUp() {
        this.textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));

        // Text selection test
        textArea.setOnContextMenuRequested(e -> Print.debug("TEST: Selected text: \n" + textArea.getSelectedText()));
        // Get the changes in strings
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            this.oldText = oldValue;
            this.newText = newValue;
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
