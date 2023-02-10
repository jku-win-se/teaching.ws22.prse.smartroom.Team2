package at.jku;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulator extends APIClient {

    public static void main(String[] args) {

        Simulator sim = new Simulator();
        Random rnd = new Random();

        // Simulator running for about 30 min, afterwards it has to be started again
        for (int i = 0; i < 180; i++) {
            // Feature: Automatically add random values of co2/temperature/number of people for a specific room
            HttpResponse response = sim.getRooms();
            List<Long> roomIDs = parseRoomIDs(response);
            List<Integer> roomSizes = parseRoomSizes(response);
            try {
                // System.out.println(roomIDs.size());
                // chose random room
                int room = rnd.nextInt(roomIDs.size());
                Long roomID = roomIDs.get(room);
                // System.out.println(room);
                // System.out.println(roomID);

                // create value for peopleInRoom with respect to the room size
                int numPeople = 1 + rnd.nextInt(roomSizes.get(room));
                // System.out.println(numPeople);

                // get random co2 value up to 1500
                double co2 = rnd.nextDouble(1500);
                // get random temperature value between -20 and 50 degrees celsius
                double temperature = rnd.nextDouble(50 + 20) - 20;


                // wait 10s
                Thread.sleep(10000);

                System.out.println("Adding " + numPeople + " people to room with id " + roomID);
                HttpResponse resp = sim.postPeopleInRoom(roomID, numPeople);
                System.out.println("Added entry: " + resp.body().toString());

                System.out.println("Adding " + co2 + " as co2 value to room with id " + roomID);
                System.out.println("Adding " + temperature + " as temperature value to room with id " + roomID);

                resp = sim.postAirQuality(roomID, co2, 0, temperature);
                System.out.println("Added entry: " + resp.body().toString());

            } catch (Exception e) {
                System.out.println("Randomizer exception caught, next value will be added in 10s!");
            }
        }
    }

    /**
     * parse Http Response for specific values
     *
     * @param response JSON HttpResponse object
     * @return a List of RoomIDs
     */
    public static List<Long> parseRoomIDs(HttpResponse response) {
        List<Long> roomIDs = getRoomIDs(response.body().toString(), "id");
        return roomIDs;
    }

    /**
     * get room ids
     *
     * @param jsonArrayStr String for json Array
     * @param key          the key String for the desired values (e.g. "id")
     * @return a List of all RoomIDs that are present in the system
     */
    public static List<Long> getRoomIDs(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optLong(key))
                .collect(Collectors.toList());
    }

    /**
     * parse Http Resonse for specific values
     *
     * @param response JSON HttpResponse object
     * @return a List of RoomIDs
     */
    public static List<Integer> parseRoomSizes(HttpResponse response) {
        List<Integer> roomIDs = getRoomSizes(response.body().toString(), "size");
        return roomIDs;
    }

    /**
     * get room ids
     *
     * @param jsonArrayStr String for json Array
     * @param key          the key String for the desired values (e.g. "id")
     * @return a List of all RoomIDs that are present in the system
     */
    public static List<Integer> getRoomSizes(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optInt(key))
                .collect(Collectors.toList());
    }
}