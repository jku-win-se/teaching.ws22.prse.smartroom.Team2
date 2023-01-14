package at.jku;

import java.awt.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportController {


    @FXML


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Open File Button");

        Button button = new Button();
        button.setText("Open File");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {

                System.out.println("hallo");
            }

            public void handle(ActionEvent event) {
                fileChooser.showOpenDialog(primaryStage);
            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    @FXML
    private void onActionImport() throws IOException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        Stage window = new Stage();
        window.setMaxHeight(200);
        window.setMaxWidth(200);
        window.setAlwaysOnTop(true);
        window.setOnCloseRequest(c -> Platform.exit());;

    }



    @FXML
    private void onActionRooms() throws IOException {
        DigitalTwinApp.setRoot("allrooms");
    }

    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionNewRoom() throws IOException {
        DigitalTwinApp.setRoot("newroom");
    }


}
