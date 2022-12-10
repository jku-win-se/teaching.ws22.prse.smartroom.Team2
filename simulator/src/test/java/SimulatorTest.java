import at.jku.Simulator;
import org.json.JSONObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class SimulatorTest {

    private JSONObject json;
    private Simulator simulator = new Simulator();
    private HttpResponse response;
    private Long id;

    @BeforeEach
    void setUp() {
//        this.json = new JSONObject();
//        this.simulator = new Simulator();
//        this.response = simulator.postRoom();
    }

    @AfterEach
    void tearDown() {
        this.json = new JSONObject(response.body().toString());
        this.id = json.getLong("id");
        this.response = simulator.deleteRoom(this.id);
    }

    @Test
    void testInsertRoomWithoutParameters() {
        this.response = this.simulator.postRoom();
        assertEquals(this.response.statusCode(), 200);
    }

    @Test
    void testInsertRoomWithName() {
        this.response = this.simulator.postRoom("SimTest");
        assertEquals(this.response.statusCode(), 200);
    }

    @Test
    void testInsertRoomWithNameAndSize() {
        this.response = this.simulator.postRoom("SimTest", 234);
        assertEquals(this.response.statusCode(), 200);
    }

    @Test
    void testPutRoomWithoutParameter() {
        this.response = simulator.postRoom();
        this.json = new JSONObject(response.body().toString());
        this.id = json.getLong("id");
        this.response = this.simulator.putRoom(this.id, "SimUpdate", 45);
        this.json = new JSONObject(response.body().toString());
        this.id = json.getLong("id");
        String name = json.getString("name");
        Integer size = json.getInt("size");
        assertEquals(this.response.statusCode(), 200);
        assertTrue(this.id != null);
        assertEquals(name, "SimUpdate");
        assertEquals(size, 45);
    }

    @Test
    void testDeleteRoom() {
        this.response = this.simulator.postRoom();
        this.json = new JSONObject(response.body().toString());
        this.id = json.getLong("id");
        this.response = this.simulator.deleteRoom(this.id);
        assertEquals(this.response.statusCode(), 200);
    }

}