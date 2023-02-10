package at.jku;

import at.jku.model.Door;

import at.jku.model.DoorRecord;
import at.jku.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

    private Door door;

    @BeforeEach
    public void setUp() {
        door = new Door();
        door.setName("Front Door");
    }

    @Test
    public void testGetId() {
        assertNull(door.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Front Door", door.getName());
    }

    @Test
    public void testAddRoom() {
        Room room = new Room();
        door.addRoom(room);
        assertEquals(1, door.getRooms(room).size());
    }

    @Test
    public void testDeleteRoom() {
        Room room = new Room();
        door.addRoom(room);
        assertEquals(1, door.getRooms(room).size());
        door.deleteRoom(room);
        assertEquals(0, door.getRooms(room).size());
    }

    @Test
    public void testGetDoorRecords() {
        assertEquals(0, door.getDoorRecords().size());
    }

    @Test
    public void testAddDoorRecord() {
        DoorRecord doorRecord = new DoorRecord();
        door.addDoorRecord(doorRecord);
        assertEquals(1, door.getDoorRecords().size());
    }

    @Test
    public void testGetState() {
        assertFalse(door.getState());
    }

    @Test
    public void testGetLatestDoorRecord() {
        assertEquals(Optional.empty(), door.getLatestDoorRecord());
    }

    @Test
    public void testSetState() {
        door.setState(true);
        assertTrue(door.getState());
    }

    @Test
    public void testIsOpen() {
        assertFalse(door.isOpen());
    }

    @Test
    public void testOpen() {
        door.open();
        assertTrue(door.isOpen());
    }

    @Test
    public void testClose() {
        door.open();
        door.close();
        assertFalse(door.isOpen());
    }

    @Test
    public void testToggle() {
        door.toggle();
        assertTrue(door.isOpen());
        door.toggle();
        assertFalse(door.isOpen());
    }
}
