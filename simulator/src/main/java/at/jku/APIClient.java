package at.jku;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

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
    public HttpResponse GET(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(uri)))
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
    public HttpResponse POST(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(uri)))
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
    public HttpResponse PUT(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(uri)))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
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
    public HttpResponse DELETE(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(uri)))
                .DELETE().build();
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
    public HttpResponse getRooms() {
        return GET(BASE_URL + "/rooms");
    }

    @Override
    public HttpResponse postRoom() {
        return POST(BASE_URL + "/rooms");
    }

    @Override
    public HttpResponse postRoom(String name) {
        return POST(BASE_URL + "/rooms" + "?name=" + name);
    }

    @Override
    public HttpResponse postRoom(String name, int size) {
        return POST(BASE_URL + "/rooms" + "?name=" + name + "&size=" + size);
    }

    @Override
    public HttpResponse getRoom(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID);
    }

    @Override
    public HttpResponse putRoom(Long roomID, String name, int size) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "?name=" + name + "&size=" + size);
    }

    @Override
    public HttpResponse deleteRoom(Long roomID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID);
    }


    @Override
    public HttpResponse getLightSources(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/lights");
    }

    @Override
    public HttpResponse postLightSource(Long roomID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/lights");
    }

    @Override
    public HttpResponse getLightSource(Long roomID, int lightID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID);
    }

    @Override
    public HttpResponse putLightSource(Long roomID, int lightID, boolean state) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID + "?state=" + state);
    }

    @Override
    public HttpResponse deleteLightSource(Long roomID, Long lightID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID);
    }

    @Override
    public HttpResponse getWindows(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/windows");
    }

    @Override
    public HttpResponse postWindow(Long roomID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/windows");
    }

    @Override
    public HttpResponse getWindow(Long roomID, Long windowID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID);
    }

    @Override
    public HttpResponse putWindow(Long roomID, int windowID, boolean state) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID + "?state=" + state);
    }

    @Override
    public HttpResponse deleteWindow(Long roomID, Long windowID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID );
    }


    @Override
    public HttpResponse getVentilators(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/ventilators");
    }

    @Override
    public HttpResponse postVentilator(Long roomID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/ventilators");
    }

    @Override
    public HttpResponse getVentilator(Long roomID, int ventilatorID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "/" + ventilatorID);
    }

    @Override
    public HttpResponse putVentilator(Long roomID, int ventilatorID, boolean state) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "/" + ventilatorID + "?state=" + state);
    }

    @Override
    public HttpResponse deleteVentilator(Long roomID, Long ventilatorID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "/" + ventilatorID);
    }


    @Override
    public HttpResponse getPeopleInRoom(Long roomID) {
        return null;
    }

    @Override
    public HttpResponse postPeopleInRoom(Long roomID, LocalDateTime timestamp, int peopleInRoom, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getTemperature(Long roomID) {
        return null;
    }

    @Override
    public HttpResponse postTemperature(Long roomID, LocalDateTime timestamp, double temperature, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getCo2(Long roomID) {
        return null;
    }

    @Override
    public HttpResponse postCo2(Long roomID, LocalDateTime timestamp, double co2, boolean state) {
        return null;
    }

    @Override
    public HttpResponse getHumidity(Long roomID) {
        return null;
    }

    @Override
    public HttpResponse postHumidity(Long roomID, LocalDateTime timestamp, double humidity, boolean state) {
        return null;
    }
}
