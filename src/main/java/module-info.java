module CT1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.base;
    requires java.logging;
    requires java.sql;

    opens messenger.client to javafx.fxml;
    exports messenger.client;

    opens messenger.server to javafx.fxml;
    exports messenger.server;

    opens CT1 to javafx.fxml;
    exports CT1;
}