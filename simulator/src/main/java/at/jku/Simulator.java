package at.jku;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;

public class Simulator extends APIClient {

    public static void main(String[] args) {
        // TODO implement Simulator

        Simulator simulator = new Simulator();


//        simulator.print(simulator.postRoom("SimulatorTest1"));
//        simulator.print(simulator.postRoom("SimulatorTest2"));
//        simulator.print(simulator.postRoom("SimulatorTest3"));
//        simulator.print(simulator.getRooms());
//        simulator.print(simulator.putRoom(2, "UpdateTest2", 30));
//        simulator.print(simulator.getRooms());
//        simulator.parseRoom(simulator.getRoom(6));
//        simulator.print(simulator.deleteRoom(2));

    }

    // lights must be turned off
    public void allLeaveRoom(Long roomID) {
        postPeopleInRoom(roomID, 0);
    }


    public JSONObject parseRoom(HttpResponse response) {
        JSONObject rooms = new JSONObject(response.body().toString());
        long id = rooms.getLong("id");
        String name = rooms.getString("name");
        int size = rooms.getInt("size");
        JSONArray objects = rooms.getJSONArray("lightSources");

        System.out.println(id + " " + name + " " + size);
        return rooms;
    }
}