package at.jku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.ResourceBundle;

import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

    Preferences prefs = Preferences.userRoot().node(this.getClass().getName());


    @FXML
    private void onActionSettings() throws IOException, BackingStoreException {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Preference name");
        String name = null;
        // setHeaderText
        td.setHeaderText("Enter a name for the preference");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            name = td.getEditor().getText();
            lblWelcome.setText("Welcome, " + name + ".");
            userName = prefs.get("userName", "User");
            prefs.exportNode(new FileOutputStream("prefs.xml"));
        }
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

    String userName;


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

    public int getCurrentCo2(Long room_id) {

        int no = 0;
        HttpResponse res = getAirQualityCo2(1L);
        JSONObject json = new JSONObject(res.body().toString());
        System.out.println(json);
        int temp = json.getInt("Co2");
        return temp;
    }


    @FXML
    BorderPane bp;

    @FXML
    VBox vbRoomSetup;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

        int noDoors =getNumberOfDoors(room_id);
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
        //lblCo2.setText(String.valueOf(getCurrentCo2(room_id)));





        HttpResponse res = getRoom(room_id); //gets the current room
        JSONObject json = new JSONObject(res.body().toString());
        int size = json.getInt("size");
        long id = json.getLong("id");
        String name = json.getString("name");
        lblSize.setText(String.valueOf(size));
        lblRoomId.setText(name);

        Label lbl = null;
        Image img = null;

        int noOfElements = noDoors+noFans+noLightBulbs+noWindows;
        boolean addedElement;
        HBox hb = new HBox();

        Slider slider;
        boolean status = false;
        HttpResponse respDevice = null;


        for (int i=1; i<=noOfElements; i++) {
            slider = new Slider(0, 1, 1);
            System.out.println("Loop" + i);


            addedElement = false;
            if (tempNoDoors > 0) {
                int temp = noDoors - tempNoDoors + 1;
                lbl = new Label("Door #" + temp);
                img = new Image(getClass().getResourceAsStream("door.png"));
                addedElement = true;
                tempNoDoors--;
                respDevice = getDoorState(room_id, (long) temp);
                JSONObject state = new JSONObject(respDevice.body().toString());
                double initialValue = 0.0;

                if (state.getBoolean("state") == true) {
                    initialValue = 1.0;
                }

                slider.setValue(initialValue);

                slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {

                        if (oldValue.intValue() !=  newValue.intValue()) {
                            HttpResponse newRes = postDoorState(room_id, (long) temp);
                            System.out.println("Door changed");
                            System.out.println(newRes.body().toString());
                            newRes = getDoorState(room_id, (long) temp);
                        }
                    }});
            }
            if (!addedElement && tempNoWindows > 0) {
                int temp = noWindows - tempNoWindows + 1;
                lbl = new Label("Window #" + temp);
                img = new Image(getClass().getResourceAsStream("window.png"));
                addedElement = true;
                tempNoWindows--;
                respDevice = getWindowState(room_id, (long) temp);
                JSONObject state = new JSONObject(respDevice.body().toString());
                double initialValue = 0.0;
                if ( state.getBoolean("state") == true){
                    initialValue = 1.0;
                }
                slider.setValue(initialValue);
                slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {

                        if (oldValue.intValue() !=  newValue.intValue()) {
                            HttpResponse newRes = postWindowState(room_id, (long) temp);
                            System.out.println(newRes.body().toString());
                            System.out.println("Window changed");}
                    }});
            }

            if (!addedElement && tempNoLightBulbs > 0) {
                int temp = noLightBulbs - tempNoLightBulbs + 1; //id of this specific device
                lbl = new Label("LightBulb #" + temp);
                img = new Image(getClass().getResourceAsStream("lightbulb.png"));
                addedElement = true;
                tempNoLightBulbs--;
                respDevice = getLightSourceActivation(room_id, temp);
                JSONObject state = new JSONObject(respDevice.body().toString());
                double initialValue = 0.0;
                if ( state.getBoolean("on") == true){
                    initialValue = 1.0;
                }
                slider.setValue(initialValue);
                System.out.println(respDevice.body().toString());
                slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {


                        if (oldValue.intValue() > newValue.intValue()) {
                            System.out.println("Turn off light source");
                            HttpResponse res = postLightSourceActivation(room_id, (long) temp, false);
                            System.out.println(res.body().toString());


                        }
                        else if (oldValue.intValue() < newValue.intValue())
                        {
                            System.out.println("Turn on light source!");
                            HttpResponse res = postLightSourceActivation(room_id, (long) temp, true);
                            System.out.println(res.body().toString());
                        }
                    }});
            }

            if (!addedElement && tempNoFans > 0) {
                int temp = noFans - tempNoFans + 1; //current id for device
                lbl = new Label("Ventilator #" + temp);
                img = new Image(getClass().getResourceAsStream("fan.png"));
                tempNoFans--;
                respDevice = getVentilatorState(room_id, temp);
                JSONObject state = new JSONObject(respDevice.body().toString());
                System.out.println(respDevice.body().toString());
                double initialValue = 0.0;
                if ( state.getBoolean("state") == true){
                    initialValue = 1.0;
                }
                slider.setValue(initialValue);
                slider.valueProperty().addListener(new ChangeListener<Number>() {

                    @Override
                    public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {


                        if (oldValue.intValue() > newValue.intValue()) {
                            System.out.println("Turn off fan");
                            HttpResponse newRes =  postVentilatorState(room_id, temp, false);
                            System.out.println(newRes.body().toString());

                        }
                        else if (oldValue.intValue() < newValue.intValue())
                        {
                            System.out.println("Turn on fan!");
                            postVentilatorState(room_id, temp, true);
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

            if (i%2!=0)
            {
                hb = new HBox();
                vbRoomSetup.getChildren().add(hb);
            }
            hb.getChildren().add(lbl);
            hb.getChildren().add(slider);
            hb.setMargin(iv, new Insets(15, 0, 0, 0));
            hb.setMargin(lbl, new Insets(10, 0, 0, 50));
            hb.setMargin(slider, new Insets(10, 0, 0, 50));


            // vbRoomSetup.setMargin(hb, new Insets())
        }

        userName = prefs.get("userName", "");
        lblWelcome.setText("Welcome, " + userName + ".");
    }

}