package db;

public class DBMain {

    public static void main (String[] args) {
        DBConnection con = new DBConnection();
        con.openConnection();
        DBFeatures.recreateDBTables(con);
        con.closeConnection();
    }
}