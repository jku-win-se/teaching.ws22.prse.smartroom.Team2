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


public class AllRoomsController extends APIClient  implements Initializable {


    String fileName = "default";
    @FXML
    private void onActionHome() throws IOException {
        DigitalTwinApp.setRoot("primary");
    }

    @FXML
    private void onActionImport() throws IOException {
        DigitalTwinApp.setRoot("import");
    }

    @FXML
    private void onActionNewRoom() throws IOException {
        DigitalTwinApp.setRoot("newroom");
    }



    @FXML
    private void onActionCheckBox() throws IOException {
        //TODO: add action for checkbox: pick room for export
    }



    @FXML
    ImageView btnSearch1;

    @FXML
    ImageView btnSearch2;


    @FXML
    ImageView delete1;

    @FXML
    ImageView delete2;


    @FXML
    private void onActionExport() throws IOException {
        textField.setVisible(true);
        btnExportOK.setVisible(true);
        btnSelectAll.setVisible(true);

        for (Node node :
                vb.getChildren()) {
            if (node.getId() != null && node.getId().equals("btnSelectAll"))
            {break;}
            ((CheckBox)((((HBox)node).getChildren()).get(2))).setVisible(true);
        }

    }
    @FXML
    private void onActionSearch() throws IOException {
       //TODO: View Room
    }

    @FXML
    CheckBox checkbox1;

    @FXML
    CheckBox checkbox2;

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
            if (node.getId() != null && node.getId().equals("btnSelectAll"))
            {break;}
            ((CheckBox)((((HBox)node).getChildren()).get(2))).setSelected(true);
        }
    }

    //TODO: Only export selected data
    private List<String[]> sampleData(){
        String[] header = {"id", "name", "size"};
        String[] record = new String[3];

        List<String[]> list = new ArrayList<>();
        list.add(header);

        for (int i=1; i<10; i++) {
            HttpResponse response = getRoom(i);
            if (response != null && response.body().toString().length()>0) {
                if (response.body().toString().charAt(0) == '{'){
            JSONObject json = new JSONObject(response.body().toString());
            long id = json.getLong("id");
            String name = json.getString("name");
            int size = json.getInt("size");
            JSONArray objects = json.getJSONArray("lightSources");
            list.add(new String[]{id+"", name, size+""});
        }}}
        return list;
    }
    @FXML
    private void onActionExportButton() throws IOException {

        List<String[]> csvData = sampleData();
        fileName = textField.getText();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName +".csv"))) {
            writer.writeAll(csvData);

        }}

    @FXML
    BorderPane bp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        vb= new VBox();
        vb.setId("mainVb");
        bp.setMargin(vb, new Insets(30, 0,0,20));

        //TODO: getrooms didnt work so i parsed each with getRoom
        for (int i=1; i<10; i++) {

            HttpResponse response = getRoom(i);
            if (response != null && response.body().toString().length()>0) {
                if (response.body().toString().charAt(0) == '{'){


            ImageView iv = new ImageView();
            iv.setFitWidth(15);
            iv.setFitHeight(15);
            Image image = new Image(getClass().getResourceAsStream("trash.png"));
            iv.setImage(image);

            CheckBox cb = new CheckBox();
            cb.setId("cb"+1);
            cb.setVisible(false);



            JSONObject json = new JSONObject(response.body().toString());
            String name = json.getString("name");
            int size = json.getInt("size");
            int id = json.getInt("id");
            // System.out.println(id + " " + name + " " + size);

            Label label = new Label(id + "       " + name);
            label.setFont(new Font("System", 20));
            label.setPrefWidth(250);
            HBox hb = new HBox();
            hb.getChildren().add(label);
            iv.setId("trash"+i);
            int finalI = i;
            iv.setOnMouseClicked(event ->  {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete room");
                alert.setHeaderText("You are about to delete this room.");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    deleteRoom(finalI);

                } else {
                    // don't delete room
                }
            });
            hb.getChildren().add(iv);
            hb.getChildren().add(cb);

            hb.setMargin(iv, new Insets(5, 20,0, 20));
            hb.setMargin(cb, new Insets(5, 0,0, 0));
            vb.getChildren().add(hb);
        }}}
        vb.getChildren().add(btnSelectAll);
        vb.getChildren().add(textField);
        vb.getChildren().add(btnExportOK);
        bp.setCenter(vb);
    }
}
