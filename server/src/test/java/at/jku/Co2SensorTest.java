package at.jku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import at.jku.model.AirQualityDevice;
import at.jku.model.Co2Sensor;
import at.jku.model.Co2SensorRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Co2SensorTest {

    private Co2Sensor co2Sensor;
    private AirQualityDevice airQualityDevice;

    @BeforeEach
    public void setUp() {
        airQualityDevice = new AirQualityDevice();
        co2Sensor = new Co2Sensor();
        co2Sensor.setAirQualityDevice(airQualityDevice);
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        co2Sensor.setId(id);
        assertEquals(id, co2Sensor.getId());
    }

    @Test
    public void testGetAirQualityDevice() {
        assertEquals(airQualityDevice, co2Sensor.getAirQualityDevice());
    }

    @Test
    public void testGetCo2SensorRecords() {
        assertNotNull(co2Sensor.getCo2SensorRecords());
        assertTrue(co2Sensor.getCo2SensorRecords().isEmpty());
    }

    @Test
    public void testAddCo2SensorRecord() {
        Co2SensorRecord co2SensorRecord = new Co2SensorRecord();
        co2Sensor.addCo2SensorRecord(co2SensorRecord);
        assertEquals(1, co2Sensor.getCo2SensorRecords().size());
        assertEquals(co2SensorRecord, co2Sensor.getCo2SensorRecords().get(0));
    }

    @Test
    public void testSetCo2() {
        double co2 = 800.0;
        co2Sensor.setCo2(co2);
        assertEquals(1, co2Sensor.getCo2SensorRecords().size());
        assertEquals(co2, co2Sensor.getCo2SensorRecords().get(0).getCo2());
    }

    @Test
    public void testGetCo2() {
        double co2 = 800.0;
        co2Sensor.setCo2(co2);
        assertEquals(co2, co2Sensor.getCo2());
    }

    @Test
    public void testGetLatestCo2SensorRecord() {
        Co2SensorRecord co2SensorRecord = new Co2SensorRecord();
        co2Sensor.addCo2SensorRecord(co2SensorRecord);
        Optional<Co2SensorRecord> latestCo2SensorRecord = co2Sensor.getLatestCo2SensorRecord();
        assertTrue(latestCo2SensorRecord.isPresent());
        assertEquals(co2SensorRecord, latestCo2SensorRecord.get());
    }
}