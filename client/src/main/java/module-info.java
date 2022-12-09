module at.jku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires org.json;
    requires com.opencsv;
    requires java.desktop;

    opens at.jku to javafx.fxml;
    exports at.jku;
}