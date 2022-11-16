package db;

import java.sql.*;

public class DBConnection {
    private String dbUrl = "prse.cbp1ffycgxjz.eu-central-1.rds.amazonaws.com";
    private String dbPort = "3306";
    private String dbUser = "admin";
    private String dbPassword = "ZFJBEagD0oqU6cLyUNpp";
    private String jdbcConnectionString = "jdbc:mysql://" + dbUrl +":"+ dbPort+ "?user=" + dbUser + "&password=" + dbPassword;

    private Connection dbConnection;

    public DBConnection() {
        this.dbConnection = null;
    }

    public Connection getDBConnection() {
        return this.dbConnection;
    }

    public void openConnection() {
        if (dbConnection != null) {
            System.out.println("DatabaseConnection already established");
            return;
        }

        try {
            dbConnection = DriverManager.getConnection(jdbcConnectionString);
            System.out.println("DatabaseConnection established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void closeConnection() {
        if (dbConnection == null) {
            throw new IllegalStateException("DatabaseConnection was already closed");
        }

        try {
            dbConnection.close();
            System.out.println("DatabaseConnection closed");
        } catch (SQLException e) {
            throw new RuntimeException("Could not close DatabaseConnection", e);
        }
    }
}