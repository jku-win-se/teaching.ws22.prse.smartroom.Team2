package at.jku;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class NewRoomController extends APIClient {


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
        alert.setHeaderText("You are about to create this room.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){;
            String name =  txtName.getText();
            postRoom(name);

        } else {
            // don't create new room
        }
    }

    @FXML
    TextField txtName;

    @FXML
    TextField txtSize;

    @FXML
    TextField txtWindows;

    @FXML
    TextField txtFans;

    @FXML
    TextField txtDoor;

    @FXML
    TextField txtLightSource;

    @FXML
    Button btnSave;

    @FXML
    Button btnCancel;

    @FXML
    private void onActionCancel() throws IOException {

        txtName.setText("");
        txtSize.setText("");
        txtWindows.setText("");
        txtFans.setText("");
        txtDoor.setText("");
        txtLightSource.setText("");

    }
}
