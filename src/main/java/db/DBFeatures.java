package db;

import java.sql.SQLException;

public class DBFeatures {
    private static final String DROP_DATABASE = "DROP DATABASE prse";
    private static final String CREATE_DATABASE = "CREATE DATABASE prse";
    private static final String USE_DATABASE = "USE prse";
    private static final String CREATE_ROOM =
            "CREATE TABLE room (roomID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(255) NOT NULL, size int)";
    private static final String CREATE_VENTILATOR =
            "CREATE TABLE ventilator (ventilatorID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_LIGHTSOURCE =
            "CREATE TABLE lightsource (lightsourceID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_DOOR =
            "CREATE TABLE door (doorID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_WINDOW =
            "CREATE TABLE win_dow (windowID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_AIR_QUALITY_SENSOR =
            "CREATE TABLE airqualitysensor (airqualitysensorID " +
                    "INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_VENTILATOR_RECORDS =
            "CREATE TABLE " +
                    "ventilator_records (timestamp datetime, ventilatorID int, isOn BOOLEAN, " +
                    "CONSTRAINT fk_vent_id FOREIGN KEY(ventilatorID)"
                    + "REFERENCES ventilator(ventilatorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_LIGHTSOURCE_RECORDS =
            "CREATE TABLE " +
                    "lightsource_records (timestamp datetime, lightsourceID int, isOn BOOLEAN, " +
                    "CONSTRAINT fk_light_id FOREIGN KEY(lightsourceID)"
                    + "REFERENCES lightsource(lightsourceID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_DOOR_RECORDS =
            "CREATE TABLE " +
                    "door_records (timestamp datetime, doorID int, isOpen BOOLEAN, " +
                    "CONSTRAINT fk_door_id FOREIGN KEY(doorID)"
                    + "REFERENCES door(doorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_WINDOW_RECORDS =
            "CREATE TABLE " +
                    "win_dow_records (timestamp datetime, windowID int, isOpen BOOLEAN, " +
                    "CONSTRAINT fk_window_id FOREIGN KEY(windowID)"
                    + "REFERENCES win_dow(windowID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_AIRQUALITY_SENSOR_RECORDS =
            "CREATE TABLE " +
                    "airqualitysensor_records (timestamp datetime, airqualitysensorID int, isOn BOOLEAN, " +
                    "CONSTRAINT fk_airqualitysensor_id FOREIGN KEY(airqualitysensorID)"
                    + "REFERENCES airqualitysensor(airqualitysensorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    public static void recreateDBTables(DBConnection con) {
        try {
            con.getDBConnection().createStatement().execute(DROP_DATABASE);
        } catch (SQLException sqle) {
            System.out.println("Database Deletion Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DATABASE);
        } catch (SQLException sqle) {
            System.out.println("Database Creation Error");
        }

        try {
            con.getDBConnection().createStatement().execute(USE_DATABASE);
        } catch (SQLException sqle) {
            System.out.println("Database Use Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_ROOM);
        } catch (SQLException sqle) {
            System.out.println("Room Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_VENTILATOR);
        } catch (SQLException sqle) {
            System.out.println("Ventilator Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_LIGHTSOURCE);
        } catch (SQLException sqle) {
            System.out.println("LightSource Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR);
        } catch (SQLException sqle) {
            System.out.println("Door Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_WINDOW);
        } catch (SQLException sqle) {
            System.out.println("Window Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_AIR_QUALITY_SENSOR);
        } catch (SQLException sqle) {
            System.out.println("AirQualitySensor Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_VENTILATOR_RECORDS);
        } catch (SQLException sqle) {
            System.out.println("VentilatorRecords Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_LIGHTSOURCE_RECORDS);
        } catch (SQLException sqle) {
            System.out.println("LightSourceRecords Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR_RECORDS);
        } catch (SQLException sqle) {
            System.out.println("DoorRecords Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_WINDOW_RECORDS);
        } catch (SQLException sqle) {
            System.out.println("WindowRecords Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_AIRQUALITY_SENSOR_RECORDS);
        } catch (SQLException sqle) {
            System.out.println("AirQualitySensorRecords Creation Error");
        }
    }
}