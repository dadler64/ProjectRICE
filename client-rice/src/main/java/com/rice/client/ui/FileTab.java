package com.rice.client.ui;

import com.rice.client.util.Print;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.util.ArrayList;
import java.util.List;

public class FileTab extends Tab {

    private final CodeArea textArea;
    private List<String> fileLines = new ArrayList<>();
    private boolean saved = false;
    private String filePath = null;
    private String fileName;

    public FileTab(String fileName) {
        this.fileName = fileName;
        this.textArea = new CodeArea();
        setUp();
    }

    private void setUp() {
        this.textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Text selection test
        textArea.setOnContextMenuRequested(e -> {
            Print.debug("selected text: \n" + textArea.getSelectedText());
        });

        this.setText(fileName);
        this.setContent(scrollPane);
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
}
