package at.jku;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Simulator {
    public static void main(String[] args) {
    // Todo JUST CODE SAMPLES TO START CODING THE SIMULATOR

        //  WITHOUT SECURITY
//        HttpClient client = HttpClient.newHttpClient();
//        postRoom(client, "Room1", 20);


        //  WITH SECURITY
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
                .uri((URI.create("http://localhost:8080/rooms" + "?name=" + name + "&size=" + size)))
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