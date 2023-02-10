package at.jku;

import at.jku.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RestApplicationTests {
    @Mock
    private AirQualityDevice airQualityDevice;

    @Mock
    private Room room;

    //Unit Test: TemperatureSensor
    @Mock
    private TemperatureSensorRecord temperatureSensorRecordTS;
    private TemperatureSensor temperatureSensorTS;

    //Unit Test: TemperatureSensorRecords
    @Mock
    private TemperatureSensor temperatureSensorTSR;
    private TemperatureSensorRecord temperatureSensorRecordTSR;

    //Unit Test: HumiditySensor
    @Mock
    private HumiditySensorRecord humiditySensorRecordHS;
    private HumiditySensor humiditySensorHS;

    //Unit Test: HumiditySensorRecords
    @Mock
    private HumiditySensor humiditySensorHSR;

    private HumiditySensorRecord humiditySensorRecordHSR;

    @Mock
    private Co2Sensor co2SensorCS;
    private Co2SensorRecord co2SensorRecordCS;

    @Mock
    private Co2Sensor co2SensorCSR;
    private Co2SensorRecord co2SensorRecordCSR;


    @BeforeEach
    public void setUp() {
        airQualityDevice = mock(AirQualityDevice.class);

        room = mock(Room.class);

        // Unit Test: Co2Sensor
        co2SensorRecordCS = mock(Co2SensorRecord.class);
        co2SensorCS = new Co2Sensor();
        co2SensorCS.setAirQualityDevice(airQualityDevice);

        //Unit Test: Co2SensorRecords
        co2SensorCSR = mock(Co2Sensor.class);
        co2SensorRecordCSR = new Co2SensorRecord();
        co2SensorRecordCSR.setCo2Sensor(co2SensorCSR);

        //Unit Test: TemperatureSensor
        temperatureSensorRecordTS = mock(TemperatureSensorRecord.class);
        temperatureSensorTS = new TemperatureSensor();
        temperatureSensorTS.setAirQualityDevice(airQualityDevice);

        //Unit Test: TemperatureSensorRecords
        temperatureSensorTSR = mock(TemperatureSensor.class);
        temperatureSensorRecordTSR = new TemperatureSensorRecord();
        temperatureSensorRecordTSR.setTemperatureSensor(temperatureSensorTSR);

        //Unit Test: HumiditySensor
        humiditySensorRecordHS = mock(HumiditySensorRecord.class);
        humiditySensorHS = new HumiditySensor();
        humiditySensorHS.setAirQualityDevice(airQualityDevice);

        //Unit Test: HumiditySensorRecords
        humiditySensorHSR = mock(HumiditySensor.class);
        humiditySensorRecordHSR = new HumiditySensorRecord();
        humiditySensorRecordHSR.setHumiditySensor(humiditySensorHSR);

    }

    //=============================================================================
    //UNIT TEST FOR: TEMPERATURE
    //Unit Test: TemperatureSensor
    @Test
    void testGetIdTS() {
        Long id = 1L;
        temperatureSensorTS.setId(id);
        assertEquals(id, temperatureSensorTS.getId());
    }

    @Test
    void testGetAirQualityDeviceTS() {
        assertNotNull(temperatureSensorTS.getAirQualityDevice());
    }

    @Test
    void testGetTemperatureTS() {
        double temperature = 22.0;
        when(temperatureSensorRecordTS.getTemperature()).thenReturn(temperature);
        when(temperatureSensorRecordTS.getTimestamp()).thenReturn(LocalDateTime.now());
        temperatureSensorTS.addTemperatureSensorRecord(temperatureSensorRecordTS);
        assertEquals(temperature, temperatureSensorTS.getTemperature());
    }

    @Test
    void testGetLatestTemperatureSensorRecordTS() {
        when(temperatureSensorRecordTS.getTimestamp()).thenReturn(LocalDateTime.now());
        temperatureSensorTS.addTemperatureSensorRecord(temperatureSensorRecordTS);
        Optional<TemperatureSensorRecord> latestTemperatureSensorRecord = temperatureSensorTS.getLatestTemperatureSensorRecord();
        assertNotNull(latestTemperatureSensorRecord);

        if (latestTemperatureSensorRecord.isPresent()) {
            assertEquals(temperatureSensorRecordTS, latestTemperatureSensorRecord.get());
        } else {
            fail("The latest temperature sensor record should not be null.");
        }
    }

    //Unit Test: TemperatureSensorRecords
    @Test
    void testGetIdTSR() {
        Long id = 1L;
        temperatureSensorRecordTSR.setId(id);
        assertEquals(id, temperatureSensorRecordTSR.getId());
    }

    @Test
    void testGetTemperatureSensorTSR() {
        assertNotNull(temperatureSensorRecordTSR.getTemperatureSensor());
    }

    @Test
    void testGetTemperatureTSR() {
        double temperature = 22.0;
        temperatureSensorRecordTSR.setTemperature(temperature);
        assertEquals(temperature, temperatureSensorRecordTSR.getTemperature());
    }

    @Test
    void testGetTimestampTSR() {
        LocalDateTime timestamp = LocalDateTime.now();
        temperatureSensorRecordTSR.setTimestamp(timestamp);
        assertEquals(timestamp, temperatureSensorRecordTSR.getTimestamp());
    }

    //=============================================================================
    //UNIT TEST FOR: HUMIDITY
    //Unit Test: HumiditySensor
    @Test
    void testSetHumidityHS() {
        humiditySensorHS.setHumidity(0.0d);
        assertEquals(0.0d, humiditySensorHS.getHumidity());
    }

    @Test
    void testGetHumidityHS() {
        when(humiditySensorRecordHS.getTimestamp()).thenReturn(LocalDateTime.now());
        when(humiditySensorRecordHS.getHumidity()).thenReturn(0.0d);
        humiditySensorHS.addHumiditySensorRecord(humiditySensorRecordHS);
        assertEquals(0.0d, humiditySensorHS.getHumidity());
    }

    @Test
    void testGetLatestHumiditySensorRecordHS() {
        when(humiditySensorRecordHS.getTimestamp()).thenReturn(LocalDateTime.now());
        humiditySensorHS.addHumiditySensorRecord(humiditySensorRecordHS);
        Optional<HumiditySensorRecord> latestHumiditySensorRecord = humiditySensorHS.getLatestHumiditySensorRecord();
        assertNotNull(latestHumiditySensorRecord);

        if (latestHumiditySensorRecord.isPresent()) {
            assertEquals(humiditySensorRecordHS, latestHumiditySensorRecord.get());
        } else {
            fail("The latest humidity sensor record should not be null.");
        }
    }

    //Unit Test: HumiditySensorRecords
    @Test
    void testGetIdHSR() {
        Long id = 1L;
        humiditySensorRecordHSR.setId(id);
        assertEquals(id, humiditySensorRecordHSR.getId());
    }

    @Test
    void testGetHumiditySensorHSR() {
        assertNotNull(humiditySensorRecordHSR.getHumiditySensor());
    }

    @Test
    void testGetTimestampHSR() {
        LocalDateTime timestamp = LocalDateTime.now();
        humiditySensorRecordHSR.setTimestamp(timestamp);
        assertEquals(timestamp, humiditySensorRecordHSR.getTimestamp());
    }

    //=============================================================================
    @Test
    void testSetCo2CS() {
        co2SensorCS.setCo2(0.0d);
        assertEquals(0.0d, co2SensorCS.getCo2());
    }

    @Test
    void testGetCo2CS() {
        when(co2SensorRecordCS.getTimestamp()).thenReturn(LocalDateTime.now());
        when(co2SensorRecordCS.getCo2()).thenReturn(0.0d);
        co2SensorCS.addCo2SensorRecord(co2SensorRecordCS);
        assertEquals(0.0d, co2SensorCS.getCo2());
    }

    @Test
    void testGetLatestCo2SensorRecordCS() {
        when(co2SensorRecordCS.getTimestamp()).thenReturn(LocalDateTime.now());
        co2SensorCS.addCo2SensorRecord(co2SensorRecordCS);
        Optional<Co2SensorRecord> latestCo2SensorRecord = co2SensorCS.getLatestCo2SensorRecord();
        assertNotNull(latestCo2SensorRecord);

        if (latestCo2SensorRecord.isPresent()) {
            assertEquals(co2SensorRecordCS, latestCo2SensorRecord.get());
        } else {
            fail("The latest humidity sensor record should not be null.");
        }
    }

    //Unit Test: Co2SensorRecord
    @Test
    void testGetIdCSR() {
        Long id = 1L;
        co2SensorRecordCSR.setId(id);
        assertEquals(id, co2SensorRecordCSR.getId());
    }

    @Test
    void testGetCo2SensorRecordCS() {
        assertNotNull(co2SensorRecordCSR.getCo2Sensor());
    }

    @Test
    void testGetTimestampCS() {
        LocalDateTime timestamp = LocalDateTime.now();
        co2SensorRecordCSR.setTimestamp(timestamp);
        assertEquals(timestamp, co2SensorRecordCSR.getTimestamp());
    }


}
