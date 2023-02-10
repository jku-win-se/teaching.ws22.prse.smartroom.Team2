package at.jku;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class ImportController {


    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionRooms() throws IOException {
        DigitalTwinApp.setRoot("allrooms");
    }

    @FXML
    private void onActionNewRoom() throws IOException {
        DigitalTwinApp.setRoot("newroom");
    }
    @FXML
    Button btnImport;

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
}
