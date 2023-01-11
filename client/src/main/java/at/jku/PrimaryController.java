package at.jku;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;



public class PrimaryController extends APIClient implements Initializable  {

    //TODO: room_id übernehmen von all rooms controller oder automatisch id=1 festlegen (mit get rest methode)
    Long room_id = 1L;

    @FXML
    private void onActionRooms() throws IOException {
        DigitalTwinApp.setRoot("allrooms");
    }

    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionRefresh() throws IOException {
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
    @FXML
    Label lblWelcome;
    @FXML
    BorderPane bp;
    @FXML
    VBox vbRoomSetup;

    String userName;

    Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @FXML
    private void onActionSettings() throws IOException, BackingStoreException {
        TextInputDialog td = new TextInputDialog();
        TextField t = new TextField();

        td.setTitle("Preference name");
        String name = null;
        td.setHeaderText("Enter a name for the preference");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            name = td.getEditor().getText();
            lblWelcome.setText("Welcome, " + name + ".");
            userName = prefs.get("userName", "User");
            prefs.exportNode(new FileOutputStream("prefs.xml"));
        }
    }

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
    public void initialize (URL url, ResourceBundle resourceBundle) {

        int noDoors = getNumberOfDoors(room_id);
        int noWindows = getNumberOfWindows(room_id);
        int noLightBulbs = getNumberOfLightSources(room_id);
        int noFans = getNumberOfVentilators(room_id);


        //variables for the loop
        int tempNoDoors = noDoors;
        int tempNoWindows = noWindows;
        int tempNoLightBulbs = noLightBulbs;
        int tempNoFans = noFans;

        lblDoor.setText(String.valueOf(noDoors));
        lblWindow.setText(String.valueOf(noWindows));
        lblLightBulb.setText(String.valueOf(noLightBulbs));
        lblFan.setText(String.valueOf(noFans));
        JSONObject jo = new JSONObject(getAirQualityCo2(room_id).body().toString());
        lblCo2.setText(jo.getInt("co2") + " ppm");
        jo = new JSONObject(getAirQualityTemperature(room_id).body().toString());
        lblTemp.setText(jo.getInt("temperature") + " °C");
        JSONArray ja = new JSONArray(getPeopleInRoom(room_id).body().toString());

        for (int i = 0; i < ja.length(); i++) {
            jo = (JSONObject) ja.get(i);
            lblPeople.setText(String.valueOf(jo.getInt("nopeopleInRoom")));
        }

        HttpResponse res = getRoom(room_id);
        JSONObject json = new JSONObject(res.body().toString());
        int size = json.getInt("size");
        long id = json.getLong("id");
        String name = json.getString("name");
        lblSize.setText(size + " qm");
        lblRoomId.setText(name);

        Label lbl = null;
        Image img = null;

        int noOfElements = noDoors + noFans + noLightBulbs + noWindows;
        boolean addedElement;
        HBox hb = new HBox();
        vbRoomSetup.getChildren().add(hb);
        Slider slider;
        boolean status = false;
        HttpResponse respDevice = null;

        HttpResponse respDoors = getDoors(room_id);
        JSONArray arrDoors = new JSONArray(respDoors.body().toString());

        HttpResponse respWindows = getWindows(room_id);
        JSONArray arrWindows = new JSONArray(respWindows.body().toString());

        HttpResponse respFans = getVentilators(room_id);
        JSONArray arrFans = new JSONArray(respFans.body().toString());

        HttpResponse respLights = getLightSources(room_id);
        JSONArray arrLights = new JSONArray(respLights.body().toString());

        for (int i=1; i<=noOfElements; i++) {
            slider = new Slider(0, 1, 1);
            addedElement = false;
            if (tempNoDoors > 0) {
                int temp = noDoors - tempNoDoors;
                JSONObject jd = (JSONObject) arrDoors.get(temp);
                id = jd.getInt("id");
                lbl = new Label("Door #" + id);
                lbl.setId("door"+id);
                img = new Image(getClass().getResourceAsStream("door.png"));
                addedElement = true;
                tempNoDoors--;
                respDevice = getDoorState(room_id, id);
                JSONObject state = new JSONObject(respDevice.body().toString());
                double initialValue = 0.0;

                if (!state.toString().startsWith("{\"path")){
                    if ( state.getBoolean("state") == true){
                        initialValue = 1.0;
                    }
                }
                slider.setValue(initialValue);
                    long finalId2 = id;
                    slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {

                        if (oldValue.intValue() !=  newValue.intValue()) {
                            HttpResponse newRes = postDoorState(room_id, finalId2);
                        }
                    }});
            }
            if (!addedElement && tempNoWindows > 0) {
                int temp = noWindows - tempNoWindows;
                JSONObject jd = (JSONObject) arrWindows.get(temp);
                id =  jd.getInt("id");
                lbl = new Label("Window #" + id);
                lbl.setId("window"+id);
                img = new Image(getClass().getResourceAsStream("window.png"));
                addedElement = true;
                tempNoWindows--;
                respDevice = getWindowState(room_id,  id);
                double initialValue = 0.0;
                if (!respDevice.body().toString().isEmpty()) {
                    JSONObject state = new JSONObject(respDevice.body().toString());
                    if (!state.toString().startsWith("{\"path")){
                        if ( state.getBoolean("state") == true){
                            initialValue = 1.0;
                        }
                    } }
                slider.setValue(initialValue);
                    long finalId = id;
                    slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {

                        if (oldValue.intValue() !=  newValue.intValue()) {
                            HttpResponse newRes = postWindowState(room_id, finalId);
                        }
                    }});
            }

            if (!addedElement && tempNoLightBulbs > 0) {
                int temp = noLightBulbs - tempNoLightBulbs;
                JSONObject jd = (JSONObject) arrLights.get(temp);
                id =  jd.getInt("id");
                lbl = new Label("LightBulb #" + id);
                lbl.setId("light"+id);
                img = new Image(getClass().getResourceAsStream("lightbulb.png"));
                addedElement = true;
                tempNoLightBulbs--;
                respDevice = getLightSourceActivation(room_id, (int) id);
                double initialValue = 0.0;
                if (!respDevice.body().toString().isEmpty()){
                    JSONObject state = new JSONObject(respDevice.body().toString());
                    if (state.getBoolean("on")){
                        initialValue = 1.0;
                    }
                    slider.setValue(initialValue); }
                    long finalId1 = id;
                    slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {


                        if (oldValue.intValue() > newValue.intValue()) {
                            System.out.println("Turn off light source");
                            HttpResponse res = postLightSourceActivation(room_id, finalId1, false);
                           // System.out.println(res.body().toString());


                        }
                        else if (oldValue.intValue() < newValue.intValue())
                        {
                            System.out.println("Turn on light source!");
                            HttpResponse res = postLightSourceActivation(room_id, finalId1, true);
                            // System.out.println(res.body().toString());
                        }
                    }});
            }
            if (!addedElement && tempNoFans > 0) {
                int temp = noFans - tempNoFans;
                JSONObject jd = (JSONObject) arrFans.get(temp);
                id =  jd.getInt("id");
                lbl = new Label("Ventilator #" + id);
                lbl.setId("vent"+id);
                img = new Image(getClass().getResourceAsStream("fan.png"));
                tempNoFans--;
                respDevice = getVentilatorState(room_id, (int) id);
                JSONObject state = new JSONObject(respDevice.body().toString());
                double initialValue = 0.0;
                if (!state.toString().startsWith("{\"path")){
                    if ( state.getBoolean("state") == true){
                        initialValue = 1.0;
                    }
                }
                slider.setValue(initialValue);
                    int finalId3 = (int) id;
                    slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {


                        if (oldValue.intValue() > newValue.intValue()) {
                            HttpResponse newRes =  postVentilatorState(room_id, finalId3, false);


                        }
                        else if (oldValue.intValue() < newValue.intValue())
                        {
                            postVentilatorState(room_id, finalId3, true);
                        }
                    }});
            }
            slider.setPrefWidth(40);
            slider.setPrefHeight(15);
            slider.setMinorTickCount(0);
            slider.setMajorTickUnit(1);
            slider.setBlockIncrement(1);
            slider.setSnapToTicks(true);

            ImageView iv = new ImageView();
            iv.setFitWidth(20);
            iv.setFitHeight(20);
            iv.setImage(img);
            lbl.setGraphic(iv);
            lbl.setPrefWidth(100);

            if (i%2!=0)
            {
                hb = new HBox();
                hb.setId("hb"+i);
                vbRoomSetup.getChildren().add(hb);
            }
            hb.getChildren().add(lbl);
            hb.getChildren().add(slider);
            hb.setMargin(iv, new Insets(15, 0, 0, 0));
            hb.setMargin(lbl, new Insets(10, 0, 0, 50));
            hb.setMargin(slider, new Insets(10, 0, 0, 50));
        }
            userName = prefs.get("userName", "");
            lblWelcome.setText("Welcome, " + userName + ".");
        }

    }
