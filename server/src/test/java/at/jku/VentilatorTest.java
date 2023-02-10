package at.jku;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VentilatorTest {

    private Ventilator ventilator;

    @Before
    public void setUp() {
        ventilator = new Ventilator();
        ventilator.setName("Ventilator1");
    }

    @Test
    public void testGetId() {
        Long expectedId = 1L;
        ventilator.setId(expectedId);
        assertEquals(expectedId, ventilator.getId());
    }

    @Test
    public void testGetName() {
        String expectedName = "Ventilator1";
        assertEquals(expectedName, ventilator.getName());
    }

    @Test
    public void testGetState() {
        assertFalse(ventilator.getState());
        ventilator.setState(true);
        assertTrue(ventilator.getState());
    }

    @Test
    public void testGetLatestVentilatorRecord() {
        LocalDateTime now = LocalDateTime.now();
        ventilator.setState(true);
        Optional<VentilatorRecord> latestVentilatorRecord = ventilator.getLatestVentilatorRecord();
        assertTrue(latestVentilatorRecord.isPresent());
        assertTrue(latestVentilatorRecord.get().getState());
        assertEquals(now.getSecond(), latestVentilatorRecord.get().getTimestamp().getSecond(), 1);
    }

    @Test
    public void testPowerOn() {
        ventilator.powerOff();
        assertFalse(ventilator.isOn());
        ventilator.powerOn();
        assertTrue(ventilator.isOn());
    }

    @Test
    public void testPowerOff() {
        ventilator.powerOn();
        assertTrue(ventilator.isOn());
        ventilator.powerOff();
        assertTrue(ventilator.isOn());
    }

    @Test
    public void testTogglePower() {
        ventilator.powerOff();
        assertFalse(ventilator.isOn());
        ventilator.togglePower();
        assertTrue(ventilator.isOn());
        ventilator.togglePower();
        assertTrue(ventilator.isOn());
    }
}