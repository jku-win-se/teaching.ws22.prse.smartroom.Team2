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

    public HttpResponse getRoom(Long roomID);

    public HttpResponse putRoom(Long roomID, String name, int size);

    public HttpResponse deleteRoom(Long roomID);

    public HttpResponse getLightSources(Long roomID);

    public HttpResponse postLightSource(Long roomID);

    public HttpResponse getLightSource(Long roomID, int lightID);

    public HttpResponse putLightSource(Long roomID, int lightID, boolean state);

    HttpResponse deleteLightSource(Long roomID, Long lightID);

    public HttpResponse getWindows(Long roomID);

    public HttpResponse postWindow(Long roomID);

    HttpResponse getWindow(Long roomID, Long windowID);

    public HttpResponse putWindow(Long roomID, int windowID, boolean state);

    HttpResponse deleteWindow(Long roomID, Long windowID);

    public HttpResponse getVentilators(Long roomID);

    public HttpResponse postVentilator(Long roomID);

    public HttpResponse getVentilator(Long roomID, int ventilatorID);

    public HttpResponse putVentilator(Long roomID, int ventilatorID, boolean state);

    HttpResponse deleteVentilator(Long roomID, Long ventilatorID);

    public HttpResponse getPeopleInRoom(Long roomID);

    public HttpResponse postPeopleInRoom(Long roomID, LocalDateTime timestamp, int peopleInRoom, boolean state);

    public HttpResponse getTemperature(Long roomID);

    public HttpResponse postTemperature(Long roomID, LocalDateTime timestamp, double temperature, boolean state);

    public HttpResponse getCo2(Long roomID);

    public HttpResponse postCo2(Long roomID, LocalDateTime timestamp, double co2, boolean state);

    public HttpResponse getHumidity(Long roomID);

    public HttpResponse postHumidity(Long roomID, LocalDateTime timestamp, double humidity, boolean state);


    // TODO missing ventilator activation (needed?) - can be done with standard put
    // TODO missing lights activation (needed?)- can be done with standard put
    // TODO missing door open (needed?)- can be done with standard put
    // TODO missing window open (needed?)- can be done with standard put
    // TODO missing lights setColor (needed?) - not in requirements

}