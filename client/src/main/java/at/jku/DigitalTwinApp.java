package at.jku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * JavaFX App
 */
public class DigitalTwinApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DigitalTwinApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
//        HttpClient client = HttpClient.newHttpClient();
//        postRoom(client, "Room1", 20);


        launch();

//        HttpClient client = HttpClient
//                .newBuilder()
//                .authenticator(new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(PRODUCT_MANAGER_USERNAME,
//                                PRODUCT_MANAGER_PASSWORD.toCharArray());
//                    }
//                })
//                .build();


    }
    private static void postRoom(HttpClient client, String name, int size) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create("http://localhost:8080/addroom" + "?name=" + name + "&size=" + size)))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            print(request, response);
        } catch (IOException e) {
            System.out.println("http IOException" + e);
        } catch (InterruptedException e) {
            System.out.println("http InterruptedException" + e);
        }
    }
        private static void print(HttpRequest request, HttpResponse response) {
            System.out.println("Sent:         " + request);
            System.out.println("Received:     " + response);
            System.out.println("Received Body:" + response.body());
            //printJSON(response);
        }
}