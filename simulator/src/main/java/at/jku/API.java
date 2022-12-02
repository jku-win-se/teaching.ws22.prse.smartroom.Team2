package at.jku;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public interface API {
    public HttpResponse getRooms();
    public HttpResponse postRoom(String name);
    public HttpResponse getRoom(int roomID);
    public HttpResponse putRoom(int roomID, String name, int size);
    public HttpResponse deleteRoom(int roomID);
    public HttpResponse getLightSources(int roomID);
    public HttpResponse postLightSource(int roomID);
    public HttpResponse getLightSource(int roomID, int LightID);
    public HttpResponse putLightSource(int roomID, int LightID, boolean state);
    public HttpResponse getWindows(int roomID);
    public HttpResponse postWindow(int roomID);
    public HttpResponse getWindow(int roomID, int windowID);
    public HttpResponse putWindow(int roomID, int windowID, boolean state);
    public HttpResponse getVentilators(int roomID);
    public HttpResponse postVentilator(int roomID);
    public HttpResponse getVentilator(int roomID, int ventilatorID);
    public HttpResponse putVentilator(int roomID, int ventilatorID, boolean state);
    public HttpResponse getPeopleInRoom(int roomID);
    public HttpResponse getActualPeopleInRoom(int roomID);
    public HttpResponse postPeopleInRoom(int roomID, LocalDateTime timestamp, int peopleInRoom);



}