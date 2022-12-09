package at.jku;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Iterator;

public class APIClient implements API {

    protected HttpClient client;
    private final static String BASE_URL = "http://localhost:8080";
    private final static String USERNAME = "";
    private final static String PASSWORD = "";

    public APIClient() {
        // Create client without authentication
        this.client = HttpClient.newHttpClient();

        // alternate client with authentication
        /*
                HttpClient client = HttpClient
                        .newBuilder()
                        .authenticator(new Authenticator() {
                              @Override
                              protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(USERNAME,
                                        PASSWORD.toCharArray());
                            }
                        })
                        .build();
        */
    }


    public static void print(HttpRequest request) {
        System.out.println("Sent:         " + request);
    }

    public static void print(HttpResponse response) {
        System.out.println("Received:     " + response);
        if (response == null) {
            return;
        }
        System.out.println("Received Body:" + response.body());
        //printJSON(response);
    }

    @Override
    public HttpResponse getRooms() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(BASE_URL + "/rooms")))
                .GET().build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException e) {
            System.out.println("http IOException" + e);
        } catch (InterruptedException e) {
            System.out.println("http InterruptedException" + e);
        }
        return null;
    }

    @Override
    public HttpResponse postRoom(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(BASE_URL + "/rooms" + "?name=" + name)))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException e) {
            System.out.println("http IOException" + e);
        } catch (InterruptedException e) {
            System.out.println("http InterruptedException" + e);
        }
        return null;
    }

    @Override
    public HttpResponse getRoom(int roomID) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(BASE_URL + "/rooms" + "/" + roomID)))
                .GET().build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // print(request, response);
            return response;
        } catch (IOException e) {
            System.out.println("http IOException" + e);
        } catch (InterruptedException e) {
            System.out.println("http InterruptedException" + e);
        }
        return null;
    }

    @Override
    public HttpResponse putRoom(int roomID, String name, int size) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(BASE_URL + "/rooms" + "/" + roomID + "?name=" + name + "&size=" + size)))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // print(request, response);
            return response;
        } catch (IOException e) {
            System.out.println("http IOException" + e);
        } catch (InterruptedException e) {
            System.out.println("http InterruptedException" + e);
        }
        return null;
    }

    @Override
    public HttpResponse deleteRoom(int roomID) {
        return null;
    }

    @Override
    public HttpResponse getLightSources(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postLightSource(int roomID) {
        return null;
    }

    @Override
    public HttpResponse getLightSource(int roomID, int LightID) {
        return null;
    }

    @Override
    public HttpResponse putLightSource(int roomID, int LightID, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getWindows(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postWindow(int roomID) {
        return null;
    }

    @Override
    public HttpResponse getWindow(int roomID, int windowID) {
        return null;
    }

    @Override
    public HttpResponse putWindow(int roomID, int windowID, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getVentilators(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postVentilator(int roomID) {
        return null;
    }

    @Override
    public HttpResponse getVentilator(int roomID, int ventilatorID) {
        return null;
    }

    @Override
    public HttpResponse putVentilator(int roomID, int ventilatorID, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getPeopleInRoom(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postPeopleInRoom(int roomID, LocalDateTime timestamp, int peopleInRoom, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getTemperature(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postTemperature(int roomID, LocalDateTime timestamp, double temperature, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getCo2(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postCo2(int roomID, LocalDateTime timestamp, double co2, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getHumidity(int roomID) {
        return null;
    }

    @Override
    public HttpResponse postHumidity(int roomID, LocalDateTime timestamp, double humidity, boolean state) {
        return null;
    }
}
