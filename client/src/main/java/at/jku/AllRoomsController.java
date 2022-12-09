package at.jku;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AllRoomsController {


    String fileName = "default";
    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionCheckBox() throws IOException {
        //TODO: add action for checkbox: pick room for export
    }


    @FXML
    ImageView btnSearch1;

    @FXML
    ImageView btnSearch2;

    @FXML
    private void onActionExport() throws IOException {
        checkbox1.setVisible(true);
        checkbox2.setVisible(true);
        textField.setVisible(true);
        btnExportOK.setVisible(true);
        btnSelectAll.setVisible(true);

    }
    @FXML
    private void onActionSearch() throws IOException {
        //TODO: add eventhandler for visiting  a certain room


    }

    @FXML
    CheckBox checkbox1;

    @FXML
    CheckBox checkbox2;

    @FXML
    TextField textField;

    @FXML
    Button btnExport;

    @FXML
    Button btnExportOK;

    @FXML
    Button btnSelectAll;

    //TODO: find a way to make this more dynamic
    @FXML
    private void onActionSelectAll() throws IOException {
       checkbox1.setSelected(true);
       checkbox2.setSelected(true);
    }


    //TODO: retrieve actual data
    private List<String[]> sampleData(){
        String[] header = {"id", "size", "windows", "doors", "fans", "light sources"};
        String[] record1 = {"1", "20", "1", "1", "1", "1"};
        String[] record2 = {"2", "30", "2", "1", "2", "2"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);

        return list;
    }
    @FXML
    private void onActionExportButton() throws IOException {

        List<String[]> csvData = sampleData();

        // default all fields are enclosed in double quotes
        // default separator is a comma
        fileName = textField.getText();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName +".csv"))) {
            writer.writeAll(csvData);

        }}

   //TODO: find a way to make this more dynamic
    @FXML
    private void onActionDelete() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete room");
        alert.setHeaderText("You are about to delete this room.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

        } else {

        //delete room

            // don't delete room
        }
    }
}
