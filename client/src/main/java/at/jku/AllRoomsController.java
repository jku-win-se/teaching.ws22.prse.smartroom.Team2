package at.jku;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.json.JSONArray;
import org.json.JSONObject;


public class AllRoomsController extends APIClient implements Initializable {


    String fileName = "default";


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
    private void onActionImport() throws IOException {
        DigitalTwinApp.setRoot("import");
    }

    @FXML
    private void onActionCheckBox() throws IOException {
        //TODO: add action for checkbox: pick room for export
    }


    @FXML
    private void onActionExport() throws IOException {
        textField.setVisible(true);
        btnExportOK.setVisible(true);
        btnSelectAll.setVisible(true);

        for (Node node :
                vb.getChildren()) {

            if (node.getId() != null && node.getId().equals("btnSelectAll")) {
                break;
            }

            if (node.getId() == null) {
                (((HBox) node).getChildren()).get(5).setVisible(true);
            }
        }

    }

    @FXML
    private void onActionSearch() throws IOException {
        //TODO: View Room
    }



    @FXML
    TextField textField;

    @FXML
    Button btnExport;

    @FXML
    Button btnExportOK;

    @FXML
    Button btnSelectAll;

    @FXML
    VBox vb;


    @FXML
    private void onActionSelectAll() {

        for (Node node :
                vb.getChildren()) {

            if (node.getId() != null && node.getId().equals("btnSelectAll")) {
                break;
            }


            if (node.getId() == null) {
                CheckBox temp = (CheckBox) ((HBox) node).getChildren().get(5);
                temp.setSelected(true);
            }
        }
    }

    //TODO: Only export selected data
    private List<String[]> sampleData() {
        String[] header = {"id", "name", "size"};
        String[] record = new String[3];

        List<String[]> list = new ArrayList<>();
        list.add(header);

        for (int i = 1; i < 10; i++) {
            HttpResponse response = getRoom(Long.valueOf(i));
            if (response != null && response.body().toString().length() > 0) {
                if (response.body().toString().charAt(0) == '{') {
                    JSONObject json = new JSONObject(response.body().toString());
                    long id = json.getLong("id");
                    String name = json.getString("name");
                    int size = json.getInt("size");
                    JSONArray objects = json.getJSONArray("lightSources");
                    list.add(new String[]{id + "", name, size + ""});
                }
            }
        }
        return list;
    }

    @FXML
    private void onActionExportButton() throws IOException {

        List<String[]> csvData = sampleData();
        fileName = textField.getText();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName + ".csv"))) {
            writer.writeAll(csvData);

        }
    }

    @FXML
    BorderPane bp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HttpResponse res = getRooms();
        JSONArray ja = new JSONArray(res.body().toString());
        Label label = new Label("ID");
        label.setFont(new Font("System", 20));
        label.setPrefWidth(30);

        HBox hb = new HBox();
        hb.setId("idTitles");
        hb.getChildren().add(label);
        label = new Label("Name");
        label.setFont(new Font("System", 20));
        label.setPrefWidth(138);
        hb.getChildren().add(label);

        label = new Label("Size");
        label.setFont(new Font("System", 20));
        label.setPrefWidth(45);
        hb.getChildren().add(label);

        vb.getChildren().add(hb);

        for (int i = 0; i < ja.length(); i++) {

            JSONObject json = ja.getJSONObject(i);

            ImageView ivTrash = new ImageView();
            ivTrash.setFitWidth(15);
            ivTrash.setFitHeight(15);
            Image imgTrash = new Image(getClass().getResourceAsStream("trash.png"));
            ivTrash.setImage(imgTrash);

            ImageView ivSearch = new ImageView();
            ivSearch.setFitWidth(15);
            ivSearch.setFitHeight(15);
            Image imgSearch2 = new Image(getClass().getResourceAsStream("search.png"));
            ivSearch.setImage(imgSearch2);

            CheckBox cb = new CheckBox();
            cb.setId("cb" + i);
            cb.setVisible(false);
            String name = json.getString("name");
            int size = json.getInt("size");
            int id = json.getInt("id");

            label = new Label(id+"");
            label.setFont(new Font("System", 13));
            label.setPrefWidth(30);
            hb = new HBox();
            hb.getChildren().add(label);

            label = new Label(name);
            label.setFont(new Font("System", 13));
            label.setPrefWidth(140);
            hb.getChildren().add(label);

            label = new Label(size +"");
            label.setFont(new Font("System", 13));
            label.setPrefWidth(45);
            hb.getChildren().add(label);

            ivTrash.setId("trash" + i);
            ivTrash.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete room");
                alert.setHeaderText("You are about to delete this room.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteRoom(Long.valueOf(id));
                    try {
                        DigitalTwinApp.setRoot("allrooms");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // don't delete room
                }
            });


            ivSearch.setId("detail" + i);
            ivSearch.setOnMouseClicked(event -> {
                try {
                    DigitalTwinApp.setRoot("primary");
                } catch (IOException e) {
                    e.printStackTrace();

                }
            });
            hb.getChildren().add(ivTrash);
            hb.getChildren().add(ivSearch);
            hb.getChildren().add(cb);

            hb.setMargin(ivTrash, new Insets(0, 0, 5, 10));
            hb.setMargin(ivSearch, new Insets(0, 0, 5, 10));
            hb.setMargin(cb, new Insets(0, 0, 5, 10));
            vb.getChildren().add(hb);
        }
    }
}
