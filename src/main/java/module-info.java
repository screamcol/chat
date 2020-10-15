module client {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.apache.commons.codec;
    opens client;
}