package at.jku;

import at.jku.model.Door;
import at.jku.model.DoorRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class DoorRecordTest {
    private DoorRecord doorRecord;

    @BeforeEach
    public void setUp() {
        doorRecord = new DoorRecord();
    }

    @Test
    public void testId() {
        Long id = 1L;
        doorRecord.setId(id);
        assertEquals(id, doorRecord.getId());
    }

    @Test
    public void testDoor() {
        Door door = new Door();
        doorRecord.setDoor(door);
        assertEquals(door, doorRecord.getDoor());
    }

    @Test
    public void testTimestamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        doorRecord.setTimestamp(timestamp);
        assertEquals(timestamp, doorRecord.getTimestamp());
    }

    @Test
    public void testState() {
        doorRecord.setState(true);
        assertTrue(doorRecord.getState());
    }
}
