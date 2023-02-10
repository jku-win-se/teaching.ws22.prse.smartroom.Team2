package at.jku;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class VentilatorRecordTest {

    @Test
    public void testGetId() {
        VentilatorRecord record = new VentilatorRecord();
        record.setId(1L);
        assertEquals(1L, record.getId().longValue());
    }

    @Test
    public void testGetVentilator() {
        VentilatorRecord record = new VentilatorRecord();
        Ventilator ventilator = new Ventilator();
        record.setVentilator(ventilator);
        assertEquals(ventilator, record.getVentilator());
    }

    @Test
    public void testGetTimestamp() {
        VentilatorRecord record = new VentilatorRecord();
        LocalDateTime timestamp = LocalDateTime.now();
        record.setTimestamp(timestamp);
        assertEquals(timestamp, record.getTimestamp());
    }

    @Test
    public void testGetState() {
        VentilatorRecord record = new VentilatorRecord();
        record.setState(true);
        assertTrue(record.getState());
    }

    @Test
    public void testSetState() {
        VentilatorRecord record = new VentilatorRecord();
        record.setState(false);
        assertFalse(record.getState());
    }

}
