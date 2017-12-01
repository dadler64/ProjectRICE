/*
 * Copyright [2017] [Dan Adler <adlerd@wit.edu>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rice.client;

import com.rice.client.thread.ClientCommunicationThread;
import com.rice.client.ui.CustomButton;
import com.rice.client.ui.CustomSeparator;
import com.rice.client.ui.FileTab;
import com.rice.client.ui.SettingsTab;
import com.rice.client.util.Logger;
import com.rice.client.util.Print;
import com.rice.lib.Packet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Client extends Application {
    private static boolean loggedIn = false;
    private static int fileNumber = 0;
    private final boolean darkTheme = false;
    private ClientCommunicationThread clientCommThread;
    private TabPane tabPane = new TabPane();

    public static Queue<Packet> packetQueue = new LinkedBlockingQueue<>();
    public static Queue<Boolean> textAreaModified = new LinkedBlockingQueue<>();

    // List of all open tabs
    private List<FileTab> openFiles = new ArrayList<>();

    public static void main(String[] args) {
        Logger.start(Logger.ERROR);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        final VBox toolBar = new VBox(initMenuBar(new MenuBar()), initButtonBar(new HBox()));

        root.setTop(toolBar);
        root.setCenter(tabPane);

        // Create a new scene using the root
        final Scene scene = new Scene(root, 800, 600);

        if (darkTheme) {
            scene.getStylesheets().add(getClass().getResource("/css/flat_button_dark.css").toExternalForm());
            toolBar.setStyle("-fx-background-color: #333");
            root.setStyle("-fx-background-color: #333");
            tabPane.setStyle("-fx-background-color: #333");
        } else {
            scene.getStylesheets().add(getClass().getResource("/css/flat_button_light.css").toExternalForm());
        }

        // Shut everything down and clean up if you close the window
        stage.setOnHiding(event -> {
            Logger.stop();
            System.exit(0);
        });
        stage.setTitle("Project RICE - Client");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

        addNewFile("RICE Test File.txtloc ");
    }

    private MenuBar initMenuBar(MenuBar menuBar) {
        // File menu
        Menu fileMenu = new Menu("File");

        MenuItem createFile = new MenuItem("New File");
        createFile.setOnAction(e -> addNewFile());
        MenuItem openFile = new MenuItem("Open File");
        openFile.setOnAction(e -> loadFile());
        MenuItem openMultFiles = new MenuItem("Open Multiple Files");
        openMultFiles.setOnAction(e -> loadMultipleFiles());
        MenuItem save = new MenuItem("Save");
        save.setOnAction(e -> saveFile());
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(e -> saveFileAs());
        MenuItem duplicate = new MenuItem("Duplicate");
        duplicate.setOnAction(e -> duplicateFile());

        SeparatorMenuItem sepFile1 = new SeparatorMenuItem();

        MenuItem settings = new MenuItem("Settings...");
        settings.setOnAction(e -> settingsTab());

        fileMenu.getItems().addAll(createFile, openFile, openMultFiles, save, saveAs, duplicate, sepFile1, settings);

        // Edit menu
        Menu editMenu = new Menu("Edit");

        MenuItem undo = new MenuItem("Undo");
//        undo.setOnAction(e -> getCurrentFile().undo());
        MenuItem redo = new MenuItem("Redo");
//        redo.setOnAction(e -> getCurrentFile().redo());

        SeparatorMenuItem sepEdit1 = new SeparatorMenuItem();

        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");

        editMenu.getItems().addAll(undo, redo, sepEdit1, cut, copy, paste);
        // Search menu
        Menu searchMenu = new Menu("Search");
        // View menu
        Menu viewMenu = new Menu("View");
        // Encoding menu
        Menu encodingMenu = new Menu("Encoding");
        // Language menu
        Menu languageMenu = new Menu("Language");
        // Syntax menu
        Menu syntaxMenu = new Menu("Syntax");
        RadioMenuItem none = new RadioMenuItem("None");
        RadioMenuItem java = new RadioMenuItem("Java");
        none.setOnAction(e -> {
            // Set syntax level to NONE
        });
        java.setOnAction(e -> {
            // Set syntax level to JAVA
        });
        syntaxMenu.getItems().addAll(none, java);
        languageMenu.getItems().addAll(syntaxMenu);
        // Run menu
        Menu runMenu = new Menu("Run");
        // Plugins menu
        Menu pluginsMenu = new Menu("Plugins");
        // Window menu
        Menu windowMenu = new Menu("Window");
        MenuItem showDirectoryTree = new MenuItem("Show Directory Tree");
        showDirectoryTree.setOnAction(e -> {
//            directoryPane.setVisible(true);
        });
        MenuItem hideDirectoryTree = new MenuItem("Hide Directory Tree");
        showDirectoryTree.setOnAction(e -> {
//            directoryPane.setVisible(false);
        });
        windowMenu.getItems().addAll(showDirectoryTree, hideDirectoryTree);
        // Help menu
        Menu helpMenu = new Menu("?");


        menuBar.getMenus().addAll(fileMenu, editMenu, searchMenu, viewMenu, encodingMenu, languageMenu, runMenu, pluginsMenu, windowMenu, helpMenu);
        menuBar.setUseSystemMenuBar(true);
        return menuBar;
    }

    private HBox initButtonBar(HBox hbox) {
        // Create button
        Button createBtn = new CustomButton("PNG", "add", darkTheme);
        createBtn.setOnAction(e -> addNewFile());
        // Open button
        Button openBtn = new CustomButton("PNG", "file", darkTheme);
        openBtn.setOnAction(e -> loadFile());
        // Save button
        Button saveBtn = new CustomButton("PNG", "save", darkTheme);
        saveBtn.setOnAction(e -> saveFile());
        // Horizontal Separator
        Separator sep1 = new CustomSeparator();
        sep1.setVisible(!darkTheme);
        // Cut button
        Button cutBtn = new CustomButton("PNG", "cut", darkTheme);
        cutBtn.setOnAction(e -> {
            // Cut the currently selected text in the code area
        });
        // Copy button
        Button copyBtn = new CustomButton("PNG", "copy", darkTheme);
        copyBtn.setOnAction(e -> {
            // Copy the currently selected text in the code area
        });
        // Paste button
        Button pasteBtn = new CustomButton("PNG", "paste", darkTheme);
        pasteBtn.setOnAction(e -> {
            // Paste the text from the clipboard to the code area
        });
        // Horizontal separator
        Separator sep2 = new CustomSeparator();
        sep2.setVisible(!darkTheme);
        // Undo button
        Button undoBtn = new CustomButton("PNG", "undo", darkTheme);
//        undoBtn.setOnAction(e -> getCurrentFile().undo());
        // Redo button
        Button redoBtn = new CustomButton("PNG", "redo", darkTheme);
//        redoBtn.setOnAction(e -> getCurrentFile().redo());
        // Horizontal separator
        Separator sep3 = new CustomSeparator();
        sep3.setVisible(!darkTheme);
        // Share button
        Button shareBtn = new Button("Share");
        shareBtn.setOnAction(actionEvent -> {
            this.getCurrentFile().setSharing(true);
            clientCommThread = new ClientCommunicationThread(this, new Pair<>("test", "test"));
            new Thread(clientCommThread).start();
        });

        Separator sep4 = new CustomSeparator();
        sep4.setVisible(!darkTheme);
        Button testBtn = new CustomButton("PNG", "download", darkTheme);
        testBtn.setOnAction(e -> {
            Print.debug("Current file's name: " + getCurrentFile().getFileName() + "\nCurrent file's extension: " + getCurrentFile().getFileExtension());
        });

//      // Set up ButtonBar HBox
        hbox.setMinHeight(30);
        hbox.setPrefHeight(30);
        hbox.getChildren().addAll(createBtn, openBtn, saveBtn, sep1, cutBtn, copyBtn, pasteBtn, sep2, undoBtn, redoBtn, sep3, shareBtn, sep4, testBtn);

        return hbox;
    }

    /**
     * @return currently open FileTab
     */
    public FileTab getCurrentFile() {
        return (FileTab) tabPane.getSelectionModel().getSelectedItem();
    }

    /**
     *
     */
    private void addNewFile() {
        String newFile;
        if (openFiles.size() < 1) {
            newFile = "New File";
            fileNumber++;
        } else {
            newFile = "New File " + fileNumber;
            fileNumber++;
        }
        FileTab fileTab = new FileTab(newFile);
        fileTab.setOnClosed(e -> {
            for (FileTab file : openFiles) {
                if (fileTab.equals(file)) {
                    openFiles.remove(openFiles.indexOf(file));
                    Print.debug("Successfully removed " + fileTab.getText());
                    break;
                }
            }
        });
        tabPane.getTabs().add(fileTab);
        openFiles.add(fileTab);
    }

    private void addNewFile(String title) {
        FileTab fileTab = new FileTab(title);
        fileTab.setOnClosed(e -> {
            for (FileTab file : openFiles) {
                if (fileTab.equals(file)) {
                    openFiles.remove(openFiles.indexOf(file));
                    Print.debug("Successfully removed " + fileTab.getText());
                    break;
                }
            }
        });
        tabPane.getTabs().add(fileTab);
        openFiles.add(fileTab);
    }

    /**
     * Open the settings tab
     */
    private void settingsTab() {
        if (SettingsTab.getNumOpen() == 0) {
            SettingsTab settingsTab = new SettingsTab();
            tabPane.getTabs().add(settingsTab);
        } else {
            Print.warn("You can only have one settings pane open at a time.");
        }
    }

    private void duplicateFile() {
        final FileTab original = getCurrentFile();
        final String originalText = original.getTextAreaContent();
        final String originalTitle = original.getText();

        addNewFile();

        FileTab duplicate = openFiles.get(openFiles.indexOf(getCurrentFile()) + 1);
        duplicate.setText(originalText + "(1)");
        duplicate.setTextArea(originalText);
    }

    private void saveFile() {
        final FileTab openFile = getCurrentFile();
        final String path = openFile.getFilePath();
        final String text = openFile.getTextAreaContent();

        // If the file has not been saved before
        if (path == null || path.equals("") || !openFile.isSaved()) {
            saveFileAs();
        } else {
            final File file = new File(openFile.getFilePath());
            try (final Writer writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFileAs() {
        final FileTab openFile = getCurrentFile();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        final File file = fileChooser.showSaveDialog(new Stage());
        try (final Writer writer = new BufferedWriter(new FileWriter(file))) {
            file.createNewFile();
            writer.write(openFile.getTextAreaContent());
            openFile.setFilePath(file.getPath());
            openFile.setFileName(file.getName());
            openFile.setSaved(true);
            Print.debug("File path for " + file.getName() + " is ('" + getCurrentFile().getFilePath() + "')");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String prevName = getCurrentFile().getText();
        getCurrentFile().setText(file.getName());
        String newName = getCurrentFile().getText();

        if (!prevName.equals(newName)) {
            Print.debug("[ " + prevName + " -> " + newName + " ]\n");
        }
    }

    private void loadMultipleFiles() {
        Print.debug("Not a thing yet :/");
    }

    private void loadFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        final File file = fileChooser.showOpenDialog(new Stage());
        for (FileTab openFile : openFiles) {
            if (file.getName().equals(openFile.getFileName())) {
                Print.error("File ('" + file.getName() + "') is already open.");
                return;
            }
        }

        if (file != null) {
            addNewFile();
            final FileTab currentFile = openFiles.get(openFiles.size() - 1);
            try (final Scanner scanner = new Scanner(new FileReader(file))) {
                String line;
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine(); // TODO: Find out why this is throwing a NoSuchElementException on file load
                    currentFile.appendTextArea(line + "\n");
                    Print.debug("Line read: " + line);
                }
                currentFile.setFileName(file.getName());
                currentFile.setFilePath(file.getPath());
                currentFile.setSaved(true);
            } catch (IOException | NoSuchElementException e) {
//                Print.error("Failed to open file.");
                e.printStackTrace();
            }
            Print.debug("File " + file.getName() + " was successfully opened from ('" + getCurrentFile().getFilePath() + "')");
        }
    }

    private void loadDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            selectedDirectory.getAbsolutePath();
        }
    }

    private void loginUser(Pair<String, String> credentials) {
//        if (!loggedIn) {
//            loggedIn = true;
//        int port = 25000;
//        String ip = "172.0.0.1";
//        new Thread(serverCommThread = new ClientCommunicationThread(this, credentials, ip, port)).start();
        Print.info("Starting server...");
//        } else {
//            System.err.println("Warning: The server comms thread is already running!");
//        }


//        private TextField usernameField;
//        private PasswordField passwordField;
//
//        usernameField = new TextField();
//        usernameField.setPromptText("Username");
//        passwordField = new PasswordField();
//        passwordField.setPromptText("Password");
//
//        // Preset text fields for quicker testing
//        usernameField.setText("dadler");
//        passwordField.setText("password");
    }
}

