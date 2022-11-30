package at.jku.db;

import java.sql.SQLException;

public class DBModel {
    private static final String DROP_DATABASE = "DROP DATABASE prse_model";
    private static final String CREATE_DATABASE = "CREATE DATABASE prse_model";

    private static final String CREATE_DATABASE_DIGITAL_TWIN = "CREATE DATABASE digital_twin";
    private static final String USE_DATABASE = "USE prse_model";
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

    public static void createDigitalTwinDB(DBConnection con) {
        try {
            con.getDBConnection().createStatement().execute(CREATE_DATABASE_DIGITAL_TWIN);
            System.out.println("Created database DigitalTwin");
        } catch (SQLException sqle) {
            System.out.println("Database creation error: DigitalTwin");
        }
    }

    public static void recreateDBTables(DBConnection con) {
        try {
            con.getDBConnection().createStatement().execute(DROP_DATABASE);
            System.out.println("Dropped database prse");
        } catch (SQLException sqle) {
            System.out.println("Database deletion error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DATABASE);
            System.out.println("Created database prse");
        } catch (SQLException sqle) {
            System.out.println("Database creation error");
        }

        try {
            con.getDBConnection().createStatement().execute(USE_DATABASE);
            System.out.println("Using database prse");
        } catch (SQLException sqle) {
            System.out.println("Database use error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_ROOM);
            System.out.println("Created table rooms");
        } catch (SQLException sqle) {
            System.out.println("rooms creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_PEOPLE_IN_ROOM);
            System.out.println("Created table People_In_Room");
        } catch (SQLException sqle) {
            System.out.println("people_in_room creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_VENTILATOR);
            System.out.println("Created table ventilators");
        } catch (SQLException sqle) {
            System.out.println("ventilators creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_LIGHTSOURCE);
            System.out.println("Created table lightsources");
        } catch (SQLException sqle) {
            System.out.println("lightsources Creation Error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR);
            System.out.println("Created table doors");
        } catch (SQLException sqle) {
            System.out.println("doors creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_WINDOW);
            System.out.println("Created table windows");
        } catch (SQLException sqle) {
            System.out.println("windows creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_AIR_QUALITY_SENSOR);
            System.out.println("Created table airqualitysensors");
        } catch (SQLException sqle) {
            System.out.println("airqualitysensors creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_VENTILATOR_RECORDS);
            System.out.println("Created table ventilator_records");
        } catch (SQLException sqle) {
            System.out.println("ventilator_records creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_LIGHTSOURCE_RECORDS);
            System.out.println("Created table lightsource_records");
        } catch (SQLException sqle) {
            System.out.println("lightsource_records creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR_RECORDS);
            System.out.println("Created table door_records");
        } catch (SQLException sqle) {
            System.out.println("door_records creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_WINDOW_RECORDS);
            System.out.println("Created table window_records");
        } catch (SQLException sqle) {
            System.out.println("window_records creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_AIRQUALITY_SENSOR_RECORDS);
            System.out.println("Created table airqualitysensor_records");
        } catch (SQLException sqle) {
            System.out.println("airqualitysensorrecords creation error");
        }
        try {
            con.getDBConnection().createStatement().execute(CREATE_DOOR_CONNECTS_ROOM);
            System.out.println("Created table door_connects_room");
        } catch (SQLException sqle) {
            System.out.println("door_connects_room creation crror");
        }
    }
}