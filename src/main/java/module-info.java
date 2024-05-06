module Application {
	exports Application.Repository;

	exports Application.Database;
	exports Application.Model;
	exports Application.Controllers;
	exports Application;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	opens Application.Controllers;
	opens Application to javafx.fxml;





}