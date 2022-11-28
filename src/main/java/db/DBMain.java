package db;

public class DBMain {
    public static void main (String[] args) {
        DBConnection con = new DBConnection();
        con.openConnection();
        DBModel.recreateDBTables(con);
        con.closeConnection();
    }
}