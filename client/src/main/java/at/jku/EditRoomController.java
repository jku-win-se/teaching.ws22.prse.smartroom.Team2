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
            int roomID =  Integer.valueOf(textedit1.getText());
            String name =  textedit2.getText();
            int size =   Integer.valueOf(textedit3.getText());
            putRoom(roomID, name, size);
        } else {
            // don't create new room
        }
    }

    //TODO: textfield namen ändern und allgemin kompoonente
    //TODO: action namen öndern
    //TODO: edit icon funktioniert nicht warum?
    //TODO: new room methode


    @FXML
    TextField textedit1;

    @FXML
    TextField textedit2;

    @FXML
    TextField textedit3;

    @FXML
    TextField textedit4;

    @FXML
    TextField textedit5;

    @FXML
    TextField textedit6;


    @FXML
    Button btnEditSave;

    @FXML
    Button btnEditCancel;

    @FXML
    private void onActionCancel() throws IOException {

        //todo: text     usw ändern
        textedit1.setText("");
        textedit2.setText("");
        textedit3.setText("");
        textedit4.setText("");
        textedit5.setText("");
        textedit6.setText("");

    }
}
