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

	@BeforeEach
	public void setUp() {
		airQualityDevice = mock(AirQualityDevice.class);

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
}
