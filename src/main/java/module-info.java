module com.example.aziz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    opens com.example.aziz.models to javafx.base;
    opens com.example.aziz to javafx.fxml;
    exports com.example.aziz;
    exports com.example.aziz.controllers.User;
    opens com.example.aziz.controllers.User;
    exports com.example.aziz.controllers.Admin;
    opens com.example.aziz.controllers.Admin;
}