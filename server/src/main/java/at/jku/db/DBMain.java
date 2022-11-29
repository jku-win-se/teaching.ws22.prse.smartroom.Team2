package at.jku.db;

public class DBMain {
    public static void main (String[] args) {
        DBConnection con = new DBConnection();
        con.openConnection();
//        DBModel.recreateDBTables(con);
//        DBModel.createDigitalTwinDB(con);
        con.closeConnection();
    }
}