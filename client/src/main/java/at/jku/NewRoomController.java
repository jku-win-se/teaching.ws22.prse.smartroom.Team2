package at.jku;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Random;

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


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){;
            String name =  txtName.getText();

            int size = 0;
            int doors = 0;
            int fans = 0;
            int windows = 0;
            int lightSources = 0;

            if (!txtSize.getText().isEmpty()) {
                size = Integer.parseInt(txtSize.getText()); }

            if (!txtDoor.getText().isEmpty()) {
                doors =  Integer.parseInt(txtDoor.getText());}

            if (!txtFans.getText().isEmpty()) {
                fans = Integer.parseInt(txtFans.getText());}

            if (!txtWindows.getText().isEmpty()) {
                windows = Integer.parseInt(txtWindows.getText());
            }

            if (!txtLightSource.getText().isEmpty()) {
                lightSources = Integer.parseInt(txtLightSource.getText()); }

            if (size>0) {
                postRoom(name, size); }
            else {postRoom(name);}

            Long room_id = (long) getIDLastRoom();

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

        } else

        {
        }
    }

    private int getIDLastRoom() {

        HttpResponse res = getRooms();
        JSONArray ja = new JSONArray(res.body().toString());
        JSONObject jo = ja.getJSONObject(ja.length()-1);
        return jo.getInt("id");
    }

    @FXML
    private void onActionAddRandom()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save room");
        alert.setHeaderText("You are about to create a room with random values.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        Random rand = new Random();
        int rnd_size = rand.nextInt(100);
        int rnd_lights =  (int) (Math.random()*(5-1)+1);
        int rnd_windows = (int) (Math.random()*(5-1)+1);
        int rnd_vent = rand.nextInt(2);
        int rnd_doors = (int) (Math.random()*(4-1)+1);

        postRoom(rnd_size);
        int lastID = getIDLastRoom();
        String name = "Room"+lastID;
        putRoom((long)getIDLastRoom(), name);

        for (int f=1; f<=rnd_vent; f++)
        {
            postVentilator((long) lastID);
        }

        for (int d=1; d<=rnd_doors; d++)
        {
            postDoor((long) lastID);
        }

        for (int w=1; w<=rnd_windows; w++)
        {
            postWindow((long) lastID);
        }

        for (int l=1; l<=rnd_lights; l++)
        {
            postLightSource((long) lastID);
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room created");
        alert.setHeaderText("Room successfully created a room.");
        alert.setContentText("You created a room with following values: \n ID:" + lastID + "\n"+
                "Name: " + name + "\n" + "Doors: " + rnd_doors + "\n" +
                "Windows: " + rnd_windows + "\n" +
                "Lights: " + rnd_lights + "\n" +
                "Fans: " + rnd_vent
        );
        alert.showAndWait();}
        else {

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
