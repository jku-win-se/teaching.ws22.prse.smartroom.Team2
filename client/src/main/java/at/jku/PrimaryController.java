package at.jku;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;


public class PrimaryController extends APIClient implements Initializable  {


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
    private void onActionEdit() throws IOException {
        DigitalTwinApp.setRoot("editroom");
    }

    @FXML
    ImageView btnEdit;

    @FXML
    Label lblRoomId;
    @FXML
    Label lblCo2;
    @FXML
    Label lblPeople;
    @FXML
    Label lblSize;
    @FXML
    Label lblTemp;
    @FXML
    Label lblDoor;
    @FXML
    Label lblWindow;
    @FXML
    Label lblLightBulb;
    @FXML
    Label lblFan;

    Long room_id = 1L;

    public int getNumberOfWindows(Long room_id) {

        int no = 0;
        HttpResponse res = getWindows(room_id);
        JSONArray ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            no++;
        }
        return no;}

    public int getNumberOfVentilators(Long room_id) {

        int no = 0;
        HttpResponse res = getVentilators(room_id);
        JSONArray ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            no++;
        }
        return no;}


    public int getNumberOfLightSources(Long room_id) {

        int no = 0;
        HttpResponse res = getLightSources(room_id);
        JSONArray ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            no++;
        }
        return no;}


    public int getNumberOfDoors(Long room_id) {

        int no = 0;
        HttpResponse res = getDoors(room_id);
        JSONArray ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            no++;
        }
        return no;}

    @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

        lblDoor.setText(String.valueOf(getNumberOfDoors(room_id)));
        lblWindow.setText(String.valueOf(getNumberOfWindows(room_id)));
        lblLightBulb.setText(String.valueOf(getNumberOfLightSources(room_id)));
        lblFan.setText(String.valueOf(getNumberOfVentilators(room_id)));


        HttpResponse res = getRoom(room_id);
        JSONObject json = new JSONObject(res.body().toString());
        int size = json.getInt("size");
        long id = json.getLong("id");
        String name = json.getString("name");
        lblSize.setText(String.valueOf(size));
        lblRoomId.setText(name);
        }






}
