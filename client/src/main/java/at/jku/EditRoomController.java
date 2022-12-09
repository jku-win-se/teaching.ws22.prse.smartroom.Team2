package at.jku;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class EditRoomController extends APIClient{


    @FXML
    private void onActionRooms() throws IOException {
        DigitalTwinApp.setRoot("allrooms");
    }

    @FXML
    private void onActionImport() throws IOException {
        DigitalTwinApp.setRoot("import");
    }

    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionSave() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save room");
        alert.setHeaderText("You are about to edit this room.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int roomID =  Integer.valueOf(text1.getText());
            String name =  text2.getText();
            int size =   Integer.valueOf(text3.getText());
            putRoom(roomID, name, size);
        } else {
            // don't create new room
        }
    }

    //TODO: textfield namen ändern und allgemin kompoonente
    //TODO: edit icon funktioniert nicht warum?
    //TODO: new room methode


    @FXML
    TextField text1;

    @FXML
    TextField text2;

    @FXML
    TextField text3;

    @FXML
    TextField text4;

    @FXML
    TextField text5;

    @FXML
    TextField text6;


    @FXML
    Button btnSave;

    @FXML
    Button btnCancel;

    @FXML
    private void onActionCancel() throws IOException {

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text6.setText("");

    }
}
