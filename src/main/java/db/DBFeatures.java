package db;

import java.sql.SQLException;

public class DBFeatures {
    private static final String DROP_DATABASE = "DROP DATABASE prse";
    private static final String CREATE_DATABASE = "CREATE DATABASE prse";
    private static final String USE_DATABASE = "USE prse";
    private static final String CREATE_ROOM =
            "CREATE TABLE rooms (roomID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name varchar(255) NOT NULL, size int)";
    private static final String CREATE_VENTILATOR =
            "CREATE TABLE ventilators (ventilatorID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_LIGHTSOURCE =
            "CREATE TABLE lightsources (lightsourceID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_DOOR =
            "CREATE TABLE doors (doorID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_WINDOW =
            "CREATE TABLE windows (windowID INT PRIMARY KEY AUTO_INCREMENT)";
    private static final String CREATE_AIR_QUALITY_SENSOR =
            "CREATE TABLE airqualitysensors (airqualitysensorID " +
                    "INT PRIMARY KEY AUTO_INCREMENT)";

    private static final String CREATE_VENTILATOR_RECORDS =
            "CREATE TABLE " +
                    "ventilator_records (timestamp datetime, ventilatorID int, isOn BOOLEAN, " +
                    "CONSTRAINT vr_fk_vent_id FOREIGN KEY(ventilatorID)"
                    + "REFERENCES ventilators(ventilatorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_LIGHTSOURCE_RECORDS =
            "CREATE TABLE " +
                    "lightsource_records (timestamp datetime, lightsourceID int, isOn BOOLEAN, " +
                    "CONSTRAINT lr_fk_light_id FOREIGN KEY(lightsourceID)"
                    + "REFERENCES lightsources(lightsourceID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_DOOR_RECORDS =
            "CREATE TABLE " +
                    "door_records (timestamp datetime, doorID int, isOpen BOOLEAN, " +
                    "CONSTRAINT dr_fk_door_id FOREIGN KEY(doorID)"
                    + "REFERENCES doors(doorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_WINDOW_RECORDS =
            "CREATE TABLE " +
                    "window_records (timestamp datetime, windowID int, isOpen BOOLEAN, " +
                    "CONSTRAINT wr_fk_window_id FOREIGN KEY(windowID)"
                    + "REFERENCES windows(windowID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_AIRQUALITY_SENSOR_RECORDS =
            "CREATE TABLE " +
                    "airqualitysensor_records (timestamp datetime, airqualitysensorID int, isOn BOOLEAN, " +
                    "CONSTRAINT ar_fk_airqualitysensor_id FOREIGN KEY(airqualitysensorID)"
                    + "REFERENCES airqualitysensors(airqualitysensorID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_PEOPLE_IN_ROOM =
            "CREATE TABLE " +
                    "people_in_room (timestamp datetime, roomID int, NumPeopleInRoom int, " +
                    "CONSTRAINT pir_fk_room_id FOREIGN KEY(roomID)"
                    + "REFERENCES rooms(roomID) ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String CREATE_DOOR_CONNECTS_ROOM =
            "CREATE TABLE " +
                    "door_connects_room (doorID int, roomID int, " +
                    "CONSTRAINT dcr_fk_door_id FOREIGN KEY(doorID)" +
                    "REFERENCES doors(doorID) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "CONSTRAINT dcr_fk_room_id FOREIGN KEY(roomID)" +
                    "REFERENCES rooms(roomID) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "PRIMARY KEY (doorID, roomID));";

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
            con.getDBConnection().createStatement().execute(CREATE_PEOPLE_IN_ROOM);
        } catch (SQLException sqle) {
            System.out.println("People In Room Creation Error");
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
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR_CONNECTS_ROOM);
        } catch (SQLException sqle) {
            System.out.println("Door Connects Room Creation Error");
        }
    }
}