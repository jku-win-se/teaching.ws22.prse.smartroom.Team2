package at.jku;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditRoomController extends APIClient implements Initializable {

    //TODO: get actual room_id
    long roomID = 1L;

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
            int size = Math.toIntExact(Long.valueOf(txtSize.getText()));
            String name =  txtName.getText();
            putRoom(roomID, name, size);
  } else {
            // don't create new room
        }
    }


    @FXML
    TextField txtName;

    @FXML
    TextField txtSize;

    @FXML
    Button btnEditSave;

    @FXML
    Button btnEditCancel;




    @FXML
    BorderPane bp;

    @FXML
    VBox vbDevices;


    public void setUpDevices() {


        HttpResponse res = getDoors(roomID);
        JSONArray ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            HBox hb = new HBox();
            Label label = new Label("Door");
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            vbDevices.getChildren().add(hb);
            JSONObject jo = (JSONObject) ja.get(i);
            int id = jo.getInt("id");
            label = new Label(id +"");
            hb.getChildren().add(label);
            label.setPrefWidth(50);
            String name = jo.getString("name");
            label = new Label(name );
            hb.getChildren().add(label);
            label.setPrefWidth(400);

            ImageView ivTrash = new ImageView();
            ivTrash.setFitWidth(15);
            ivTrash.setFitHeight(15);
            Image imgTrash = new Image(getClass().getResourceAsStream("trash.png"));
            ivTrash.setImage(imgTrash);

            ivTrash.setId("trash" + i);
            ivTrash.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete door");
                alert.setHeaderText("You are about to delete this door.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteDoor(roomID, (long) id);
                    try {
                        DigitalTwinApp.setRoot("editroom");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // don't delete room
                }
            });

            ImageView ivEdit = new ImageView();
            ivEdit.setFitWidth(15);
            ivEdit.setFitHeight(15);
            Image imgSearch2 = new Image(getClass().getResourceAsStream("pencil.png"));
            ivEdit.setImage(imgSearch2);

            ivEdit.setOnMouseClicked(event -> { Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);

                TextField txtName = new TextField();

                Button btnCancel = new Button("Cancel");
                Button btnSave = new Button("Save");
                btnSave.setOnAction(e -> {
                    String doorName = txtName.getText();
                    putDoor(roomID, id, doorName);
                    stage.close();
                });

                btnCancel.setOnAction(e -> {
                    stage.close();
                });

                Label label1 = new Label("Edit door  ");
                Label label2 = new Label("Name:");

                GridPane layout = new GridPane();

                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.setVgap(5);
                layout.setHgap(5);

                layout.add(txtName, 1,1);
                layout.add(btnSave, 1,3);
                layout.setMargin(btnSave, new Insets(0, 0,0,70));
                layout.add(btnCancel, 1, 3);
                layout.add(label1, 1,0);
                layout.add(label2, 0,1);
                Scene scene = new Scene(layout, 250, 130);
                stage.setTitle("Edit");
                stage.setScene(scene);
                stage.showAndWait();});

            hb.getChildren().add(ivTrash);
            hb.getChildren().add(ivEdit);
            hb.setMargin(ivTrash, new Insets(0, 10, 0, 0));
            hb.setMargin(ivEdit, new Insets(0, 10, 0, 0));
        }

        res = getWindows(roomID);
        ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            HBox hb = new HBox();
            Label label = new Label("Windows");
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            vbDevices.getChildren().add(hb);
            int id = jo.getInt("id");
            label = new Label(id +"");
            label.setPrefWidth(50);
            hb.getChildren().add(label);
            label = new Label("");
            label.setPrefWidth(200);
            hb.getChildren().add(label);
            label.setPrefWidth(400);

            ImageView ivTrash = new ImageView();
            ivTrash.setFitWidth(15);
            ivTrash.setFitHeight(15);
            Image imgTrash = new Image(getClass().getResourceAsStream("trash.png"));
            ivTrash.setImage(imgTrash);

            ivTrash.setId("trash" + i);
            ivTrash.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete window");
                alert.setHeaderText("You are about to delete this window.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteWindow(roomID, (long) id);
                    try {
                        DigitalTwinApp.setRoot("editroom");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            });

            hb.getChildren().add(ivTrash);
            hb.setMargin(ivTrash, new Insets(0, 10, 0, 0));

        }

        res = getLightSources(roomID);
        ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            HBox hb = new HBox();
            Label label = new Label("Light Source");
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            vbDevices.getChildren().add(hb);
            int id = jo.getInt("id");
            label = new Label(id +"");
            hb.getChildren().add(label);
            label.setPrefWidth(50);
            String name = jo.getString("name");
            label = new Label(name);
            hb.getChildren().add(label);
            label.setPrefWidth(200);
            String hex = "";
            if (jo.get("hex") != null) {
                hex = jo.getString("hex");
            }
            label = new Label(hex);
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            int brightness = jo.getInt("brightness");
            label = new Label(brightness +"");
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            ImageView ivTrash = new ImageView();
            ivTrash.setFitWidth(15);
            ivTrash.setFitHeight(15);
            Image imgTrash = new Image(getClass().getResourceAsStream("trash.png"));
            ivTrash.setImage(imgTrash);

            ivTrash.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete light source");
                alert.setHeaderText("You are about to delete this light source.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteLightSource(roomID, (long) id);
                    try {
                        DigitalTwinApp.setRoot("editroom");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // don't delete room
                }
            });

            ImageView ivEdit = new ImageView();
            ivEdit.setFitWidth(15);
            ivEdit.setFitHeight(15);
            Image imgSearch2 = new Image(getClass().getResourceAsStream("pencil.png"));
            ivEdit.setImage(imgSearch2);

            ivEdit.setOnMouseClicked(event -> { Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);

                TextField txtName = new TextField();
                TextField txtBrightness = new TextField();
                TextField txtHex = new TextField();

                Button btnCancel = new Button("Cancel");
                Button btnSave = new Button("Save");
                btnSave.setOnAction(e -> {

                    //TODO: Edit Light Source doesn't work
                    if (!txtName.getText().isEmpty())

                        patchLightSource(roomID, id, txtName.getText());

                    if ( !txtBrightness.getText().isEmpty())
                    {
                        postLightSourceColor(roomID, (long) id, Integer.parseInt(txtBrightness.getText()));
                    }

                    if (!txtHex.getText().isEmpty() )
                    {
                        postLightSourceColor(roomID, (long) id, txtHex.getText());
                    }
                    stage.close();
                });

                btnCancel.setOnAction(e -> {
                    stage.close();
                });

                Label label1 = new Label("Edit light source  ");
                Label label2 = new Label("Name:");
                Label label3 = new Label("Brightness:");
                Label label4 = new Label("Colour in Hex :");

                GridPane layout = new GridPane();

                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.setVgap(5);
                layout.setHgap(5);
                layout.setMargin(btnSave, new Insets(0, 0,0,70));
                layout.add(txtName, 1,1);
                layout.add(btnSave, 1,4);
                layout.add(btnCancel, 1,4);
                layout.add(txtBrightness, 1,2);
                layout.add(txtHex, 1,3);
                layout.add(label1, 0,0);
                layout.add(label2, 0,1);
                layout.add(label3, 0,2);
                layout.add(label4, 0,3);
                Scene scene = new Scene(layout, 250, 170);
                stage.setTitle("Edit light source");
                stage.setScene(scene);
                stage.showAndWait();});

            hb.getChildren().add(ivTrash);
            hb.getChildren().add(ivEdit);
            hb.setMargin(ivTrash, new Insets(0, 10, 0, 0));
            hb.setMargin(ivEdit, new Insets(0, 10, 0, 0));
        }

        res = getVentilators(roomID);
        ja = new JSONArray(res.body().toString());

        for (int i = 0; i < ja.length(); i++) {
            HBox hb = new HBox();
            Label label = new Label("Ventilator");
            hb.getChildren().add(label);
            label.setPrefWidth(100);
            vbDevices.getChildren().add(hb);
            JSONObject jo = (JSONObject) ja.get(i);
            int id = jo.getInt("id");
            label = new Label(id +"");
            hb.getChildren().add(label);
            label.setPrefWidth(50);
            String name = jo.getString("name");
            label = new Label(name );
            hb.getChildren().add(label);
            label.setPrefWidth(400);

            ImageView ivTrash = new ImageView();
            ivTrash.setFitWidth(15);
            ivTrash.setFitHeight(15);
            Image imgTrash = new Image(getClass().getResourceAsStream("trash.png"));
            ivTrash.setImage(imgTrash);
            ivTrash.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete ventilator");
                alert.setHeaderText("You are about to delete this ventilator.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteVentilator(roomID, (long) id);
                    try {
                        DigitalTwinApp.setRoot("editroom");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // don't delete ventilator
                }
            });

            ImageView ivEdit = new ImageView();
            ivEdit.setFitWidth(15);
            ivEdit.setFitHeight(15);
            Image imgSearch2 = new Image(getClass().getResourceAsStream("pencil.png"));
            ivEdit.setImage(imgSearch2);

            ivEdit.setOnMouseClicked(event -> { Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);

                TextField txtName = new TextField();

                Button btnCancel = new Button("Cancel");
                Button btnSave = new Button("Save");

                btnSave.setOnAction(e -> {
                    if (!txtName.getText().isEmpty())
                    {
                    patchVentilator(roomID, id, txtName.getText());}
                    stage.close();
                });

                btnCancel.setOnAction(e -> {
                    stage.close();
                });

                Label label1 = new Label("Edit ventilator  ");
                Label label2 = new Label("Name:");

                GridPane layout = new GridPane();

                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.setVgap(5);
                layout.setHgap(5);
                layout.add(txtName, 1,1);
                layout.add(btnCancel, 1,3);
                layout.add(btnSave, 1,3);
                layout.setMargin(btnSave, new Insets(0, 0,0,70));
                layout.add(label1, 1,0);
                layout.add(label2, 0,1);
                Scene scene = new Scene(layout, 250, 120);
                stage.setTitle("Edit");
                stage.setScene(scene);
                stage.showAndWait();});

            hb.getChildren().add(ivTrash);
            hb.getChildren().add(ivEdit);
            hb.setMargin(ivTrash, new Insets(0, 10, 0, 0));
            hb.setMargin(ivEdit, new Insets(0, 10, 0, 0));
        }

    }

    @FXML
    private void onActionAddWindow() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add window");
        alert.setHeaderText("You are about to add a window to this room");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            postWindow(roomID);
        } else {
            // don't add window
        }
    }
    @FXML
    private void onActionAddLS() throws IOException{
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField txtName = new TextField();

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if (!txtName.getText().isEmpty())

            {postLightSource(roomID, txtName.getText());}

            else {postLightSource(roomID);}

            stage.close();
        });

        btnCancel.setOnAction(e -> {
            stage.close();
        });

        Label label1 = new Label("Add light source ");
        Label label2 = new Label("Name: (optional) ");

        //TODO: Add hex and brightness too

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setMargin(btnSave, new Insets(0, 0,0,70));
        layout.add(txtName, 1,1);
        layout.add(btnSave, 1,2);
        layout.add(btnCancel, 1,2);
        layout.add(label1, 0,0);
        layout.add(label2, 0,1);
        Scene scene = new Scene(layout, 300, 120);
        stage.setTitle("Add light source");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void onActionAddDoor() throws IOException{
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField txtName = new TextField();

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            String doorName = txtName.getText();
            postDoor(roomID,doorName);
            stage.close();
        });

        btnCancel.setOnAction(e -> {
            stage.close();
        });

        Label label1 = new Label("Add door");
        Label label2 = new Label("Name (optional):");

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(txtName, 1,1);
        layout.add(btnSave, 1,3);
        layout.setMargin(btnSave, new Insets(0, 0,0,70));
        layout.add(btnCancel, 1, 3);
        layout.add(label1, 1,0);
        layout.add(label2, 0,1);
        Scene scene = new Scene(layout, 300, 120);
        stage.setTitle("Add door");
        stage.setScene(scene);
        stage.showAndWait(); }

    @FXML
    private void onActionAddVentilator() throws IOException{
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        TextField txtName = new TextField();

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            String ventilatorName = txtName.getText();
            postVentilator(roomID,ventilatorName);
            stage.close();
        });

        btnCancel.setOnAction(e -> {
            stage.close();
        });

        Label label1 = new Label("Add ventilator");
        Label label2 = new Label("Name (optional):");

        GridPane layout = new GridPane();

        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(txtName, 1,1);
        layout.add(btnSave, 1,3);
        layout.setMargin(btnSave, new Insets(0, 0,0,70));
        layout.add(btnCancel, 1, 3);
        layout.add(label1, 1,0);
        layout.add(label2, 0,1);
        Scene scene = new Scene(layout, 300, 120);
        stage.setTitle("Add ventilator");
        stage.setScene(scene);
        stage.showAndWait(); }



    @FXML
    private void onActionCancel() throws IOException {
        txtSize.setText("");
        txtName.setText("");
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        setUpDevices();

    }
}
