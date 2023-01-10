package at.jku;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public HttpResponse PATCH(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri((URI.create(uri)))
                .method("PATCH", HttpRequest.BodyPublishers.noBody())
                .build();
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
    public HttpResponse postRoom(int size) {
        return POST(BASE_URL + "/rooms" + "?size=" + size);
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
    public HttpResponse putRoom(Long roomID, String name) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "?name=" + name);
    }

    @Override
    public HttpResponse putRoom(Long roomID, int size) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "?size=" + size);
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
    public HttpResponse postLightSource(Long roomID, String name) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "?name=" + name);
    }

    @Override
    public HttpResponse getLightSource(Long roomID, int lightID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID);
    }

    @Override
    public HttpResponse patchLightSource(Long roomID, int lightID, String name) {
        return PATCH(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID + "?name=" + name);
    }

    @Override
    public HttpResponse getLightSourceActivation(Long roomID, int lightID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID + "/Activation");
    }

    @Override
    public HttpResponse postLightSourceActivation(Long roomID, Long lightID, boolean turnon) {
        return POST(BASE_URL + "/rooms" + "/" + roomID
                + "/lights" + "/" + lightID + "/Activation?turnon=" + turnon);
    }

    @Override
    public HttpResponse deleteLightSource(Long roomID, Long lightID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/lights" + "/" + lightID);
    }

    @Override
    public HttpResponse postLightSourceColor(Long roomID, Long lightID, String hex) {
        return POST(BASE_URL + "/rooms" + "/" + roomID
                + "/lights" + "/" + lightID + "?hex=" + hex);
    }

    @Override
    public HttpResponse postLightSourceColor(Long roomID, Long lightID, int brightness) {
        return POST(BASE_URL + "/rooms" + "/" + roomID
                + "/lights" + "/" + lightID + "/SetColor?brightness=" + brightness);
    }

    @Override
    public HttpResponse postLightSourceColor(Long roomID, Long lightID, String hex, int brightness) {
        return POST(BASE_URL + "/rooms" + "/" + roomID
                + "/lights" + "/" + lightID + "/SetColor?hex=" + hex + "&brightness=" + brightness);
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
    public HttpResponse putWindow(Long roomID, int windowID) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID);
    }

    @Override
    public HttpResponse deleteWindow(Long roomID, Long windowID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID);
    }

    @Override
    public HttpResponse getWindowState(Long roomID, Long windowID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID + "/Open");
    }

    @Override
    public HttpResponse postWindowState(Long roomID, Long windowID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/windows" + "/" + windowID + "/Open");
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
    public HttpResponse postVentilator(Long roomID, String name) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "?name=" + name);
    }

    @Override
    public HttpResponse getVentilator(Long roomID, int ventilatorID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "/" + ventilatorID);
    }

    @Override
    public HttpResponse patchVentilator(Long roomID, int ventilatorID, String name) {
        return PATCH(BASE_URL + "/rooms" + "/" + roomID
                + "/ventilators" + "/" + ventilatorID + "?name=" + name);
    }

    @Override
    public HttpResponse postVentilatorState(Long roomID, int ventilatorID, boolean turnon) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/ventilators"
                + "/" + ventilatorID + "/Operations?turnon=" + turnon);
    }

    @Override
    public HttpResponse getVentilatorState(Long roomID, int ventilatorID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/ventilators"
                + "/" + ventilatorID + "/Activation");
    }

    @Override
    public HttpResponse postVentilatorState(Long roomID, int ventilatorID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/ventilators"
                + "/" + ventilatorID + "/Activation");
    }

    @Override
    public HttpResponse deleteVentilator(Long roomID, Long ventilatorID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/ventilators" + "/" + ventilatorID);
    }

    @Override
    public HttpResponse getPeopleInRoom(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/PeopleInRoom");
    }

    @Override
    public HttpResponse postPeopleInRoom(Long roomID, int people_count) {
        return POST(BASE_URL + "/rooms" + "/" + roomID
                + "/PeopleInRoom" + "?people_count=" + people_count);
    }

    @Override
    public HttpResponse getDoors(Long roomID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/doors");
    }

    @Override
    public HttpResponse postDoor(Long roomID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/doors");
    }

    @Override
    public HttpResponse postDoor(Long roomID, String name) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/doors" + "?name=" + name);
    }

    @Override
    public HttpResponse getDoor(Long roomID, Long doorID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/doors/" + doorID);
    }

    @Override
    public HttpResponse putDoor(Long roomID, int doorID, String name) {
        return PUT(BASE_URL + "/rooms" + "/" + roomID + "/doors/"
                + doorID + "?name=" + name);
    }

    @Override
    public HttpResponse deleteDoor(Long roomID, Long doorID) {
        return DELETE(BASE_URL + "/rooms" + "/" + roomID + "/doors/" + doorID);
    }

    @Override
    public HttpResponse getDoorState(Long roomID, Long doorID) {
        return GET(BASE_URL + "/rooms" + "/" + roomID + "/doors/" + doorID + "/Open");
    }

    @Override
    public HttpResponse postDoorState(Long roomID, Long doorID) {
        return POST(BASE_URL + "/rooms" + "/" + roomID + "/doors/" + doorID + "/Open");
    }

    @Override
    public HttpResponse getAirQuality(Long roomID) {
        return GET(BASE_URL + "/room" + "/" + roomID + "/AirQuality");
    }

    @Override
    public HttpResponse postAirQuality(Long roomID, double co2, double humidity, double temperature) {
        return POST(BASE_URL + "/room/AirQuality"
                + "?room_id=" + roomID
                + "?co2=" + co2
                + "?humidity=" + humidity
                + "?temperature=" + temperature);
    }

    @Override
    public HttpResponse getAirQualityTemperature(Long roomID) {
        return GET(BASE_URL + "/room/" + roomID + "/AirQuality/temperature");
    }

    @Override
    public HttpResponse getAirQualityHumidity(Long roomID) {
        return GET(BASE_URL + "/room/" + roomID + "/AirQuality/humidity");
    }

    @Override
    public HttpResponse getAirQualityCo2(Long roomID) {
        return GET(BASE_URL + "/room/" + roomID + "/AirQuality/co2");
    }
}
