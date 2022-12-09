package at.jku;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public interface API {

    public HttpResponse GET(String uri);

    public HttpResponse POST(String uri);

    public HttpResponse PUT(String uri);

    public HttpResponse DELETE(String uri);
    public HttpResponse getRooms();

    public HttpResponse postRoom();

    public HttpResponse postRoom(String name);

    public HttpResponse postRoom(String name, int size);

    public HttpResponse getRoom(int roomID);

    public HttpResponse putRoom(int roomID, String name, int size);

    public HttpResponse deleteRoom(int roomID);

    public HttpResponse getLightSources(int roomID);

    public HttpResponse postLightSource(int roomID);

    public HttpResponse getLightSource(int roomID, int lightID);

    public HttpResponse putLightSource(int roomID, int lightID, boolean state);

    public HttpResponse getWindows(int roomID);

    public HttpResponse postWindow(int roomID);

    public HttpResponse getWindow(int roomID, int windowID);

    public HttpResponse putWindow(int roomID, int windowID, boolean state);

    public HttpResponse getVentilators(int roomID);

    public HttpResponse postVentilator(int roomID);

    public HttpResponse getVentilator(int roomID, int ventilatorID);

    public HttpResponse putVentilator(int roomID, int ventilatorID, boolean state);

    public HttpResponse getPeopleInRoom(int roomID);

    public HttpResponse postPeopleInRoom(int roomID, LocalDateTime timestamp, int peopleInRoom, boolean state);

    public HttpResponse getTemperature(int roomID);

    public HttpResponse postTemperature(int roomID, LocalDateTime timestamp, double temperature, boolean state);

    public HttpResponse getCo2(int roomID);

    public HttpResponse postCo2(int roomID, LocalDateTime timestamp, double co2, boolean state);

    public HttpResponse getHumidity(int roomID);

    public HttpResponse postHumidity(int roomID, LocalDateTime timestamp, double humidity, boolean state);


    // TODO missing ventilator activation (needed?) - can be done with standard put
    // TODO missing lights activation (needed?)- can be done with standard put
    // TODO missing door open (needed?)- can be done with standard put
    // TODO missing window open (needed?)- can be done with standard put
    // TODO missing lights setColor (needed?) - not in requirements

}