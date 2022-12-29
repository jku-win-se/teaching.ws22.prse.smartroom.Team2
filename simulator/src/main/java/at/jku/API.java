package at.jku;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public interface API {

    public HttpResponse GET(String uri);

    public HttpResponse POST(String uri);

    public HttpResponse PUT(String uri);

    public HttpResponse PATCH(String uri);

    public HttpResponse DELETE(String uri);

    public HttpResponse getRooms();

    public HttpResponse postRoom();

    public HttpResponse postRoom(String name);

    public HttpResponse postRoom(int size);

    public HttpResponse postRoom(String name, int size);

    public HttpResponse getRoom(Long roomID);

    public HttpResponse putRoom(Long roomID, String name);

    public HttpResponse putRoom(Long roomID, int size);

    public HttpResponse putRoom(Long roomID, String name, int size);

    public HttpResponse deleteRoom(Long roomID);

    public HttpResponse getLightSources(Long roomID);

    public HttpResponse postLightSource(Long roomID);

    public HttpResponse postLightSource(Long roomID, String name);


    public HttpResponse getLightSource(Long roomID, int lightID);

    public HttpResponse patchLightSource(Long roomID, int lightID, String name);

    public HttpResponse getLightSourceActivation(Long roomID, int lightID);

    public HttpResponse postLightSourceActivation(Long roomID, Long lightID, boolean turnon);

    public HttpResponse deleteLightSource(Long roomID, Long lightID);

    public HttpResponse postLightSourceColor(Long roomID, Long lightID, String hex);

    public HttpResponse postLightSourceColor(Long roomID, Long lightID, int brightness);

    public HttpResponse postLightSourceColor(Long roomID, Long lightID, String hex, int brightness);

    public HttpResponse getWindows(Long roomID);

    public HttpResponse postWindow(Long roomID);

    public HttpResponse getWindow(Long roomID, Long windowID);

    public HttpResponse putWindow(Long roomID, int windowID);

    HttpResponse deleteWindow(Long roomID, Long windowID);

    public HttpResponse getWindowState(Long roomID, Long windowID);

    public HttpResponse postWindowState(Long roomID, Long windowID);

    public HttpResponse getVentilators(Long roomID);

    public HttpResponse postVentilator(Long roomID);

    public HttpResponse postVentilator(Long roomID, String name);

    public HttpResponse getVentilator(Long roomID, int ventilatorID);

    public HttpResponse patchVentilator(Long roomID, int ventilatorID, String name);

    public HttpResponse postVentilatorState(Long roomID, int ventilatorID, boolean turnon);

    public HttpResponse getVentilatorState(Long roomID, int ventilatorID);

    public HttpResponse postVentilatorState(Long roomID, int ventilatorID);

    public HttpResponse deleteVentilator(Long roomID, Long ventilatorID);

    public HttpResponse getPeopleInRoom(Long roomID);

    public HttpResponse postPeopleInRoom(Long roomID, int people_count);

    public HttpResponse getDoors(Long roomID);

    public HttpResponse postDoor(Long roomID);

    public HttpResponse postDoor(Long roomID, String name);

    public HttpResponse getDoor(Long roomID, Long doorID);

    public HttpResponse putDoor(Long roomID, int doorID, String name);

    HttpResponse deleteDoor(Long roomID, Long doorID);

    public HttpResponse getDoorState(Long roomID, Long doorID);

    public HttpResponse postDoorState(Long roomID, Long doorID);

    public HttpResponse getAirQuality(Long roomID);

    public HttpResponse postAirQuality(Long roomID, double co2, double humidity, double temperature);

    public HttpResponse getAirQualityTemperature(Long roomID);

    public HttpResponse getAirQualityHumidity(Long roomID);

    public HttpResponse getAirQualityCo2(Long roomID);

}