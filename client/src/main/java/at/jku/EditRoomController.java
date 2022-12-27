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
    private void onActionNewRoom() throws IOException {
        DigitalTwinApp.setRoot("newroom");
    }

    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionImport() throws IOException {
        DigitalTwinApp.setRoot("import");
    }

    @FXML
    private void onActionSave() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save room");
        alert.setHeaderText("You are about to edit this room.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            long roomID =  Long.valueOf(txtEditId.getText());
            String name =  txtEditName.getText();
            int size =   Integer.valueOf(txtEditSize.getText());
            putRoom(roomID, name, size);
  } else {
            // don't create new room
        }
    }

    @FXML

    TextField txtEditId;

    @FXML
    TextField txtEditName;

    @FXML
    TextField txtEditSize;

    @FXML
    TextField txtEditWindow;

    @FXML
    TextField txtEditDoor;

    @FXML
    TextField txtEditFan;

    @FXML
    TextField txtEditLightSource;

    @FXML
    Button btnEditSave;

    @FXML
    Button btnEditCancel;

    @FXML
    private void onActionCancel() throws IOException {
        txtEditId.setText("");
        txtEditSize.setText("");
        txtEditName.setText("");
        txtEditWindow.setText("");
        txtEditDoor.setText("");
        txtEditFan.setText("");
        txtEditLightSource.setText("");
    }
}
