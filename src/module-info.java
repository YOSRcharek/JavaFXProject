module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires com.jfoenix;
    requires org.apache.pdfbox;
    requires javafx.swing;
    requires javafx.graphics;


    opens demo to javafx.fxml;
    exports demo;

}