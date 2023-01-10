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

    @FXML
    private void onActionImport() throws IOException {
        DigitalTwinApp.setRoot("import");
    }


    @FXML
    private void onActionSave() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save room");
        alert.setHeaderText("You are about to create this room.");
        alert.setContentText("Are you ok with this?");


        //TODO: get the ACTUAL last room_id
        Long room_id = 4L; //just for testing purposes
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){;
            String name =  txtName.getText();

            int size = 0;
            int doors = 0;
            int fans = 0;
            int windows = 0;
            int lightSources = 0;

            if (txtSize.getText() != null) {
                size = Integer.parseInt(txtSize.getText()); }

            if (txtDoor.getText() != null) {
                doors =  Integer.parseInt(txtDoor.getText());}

            if (txtFans.getText() != null) {
                fans = Integer.parseInt(txtFans.getText());}

            if (txtWindows.getText() != null) {
                windows = Integer.parseInt(txtWindows.getText());
            }

            if (txtLightSource.getText() != null) {
                lightSources = Integer.parseInt(txtLightSource.getText()); }

            if (size>0) {
                postRoom(name, size); }
            else {postRoom(name);}

            //adding devices
            for (int f=1; f<=fans; f++)
            {
                postVentilator(room_id);
            }

            for (int d=1; d<=doors; d++)
            {
                postDoor(room_id);
            }

            for (int w=1; w<=windows; w++)
            {
                postWindow(room_id);
            }

            for (int l=1; l<=lightSources; l++)
            {
                postLightSource(room_id);
            }

        } else {
            // don't create new room
        }
    }
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
