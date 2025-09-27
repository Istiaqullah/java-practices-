module CT1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens CT1 to javafx.fxml;
    exports CT1;
}